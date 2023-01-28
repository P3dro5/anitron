package com.example.anitron.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.anitron.R
import com.example.anitron.data.datasource.CategoryEntry
import com.example.anitron.data.datasource.SearchWidgetState
import com.example.anitron.data.datasource.State
import com.example.anitron.data.repository.HomeRepository
import com.example.anitron.databinding.ActivityMainBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.HomeViewModelFactory
import com.example.anitron.ui.theme.fonts
import com.example.anitron.ui.viewmodel.HomeViewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: HomeViewModel

    private val retrofitService = RetrofitService.getInstance()

    private lateinit var searchedQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var initialCall = true
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get viewmodel instance using MyViewModelFactory
        viewModel =
            ViewModelProvider(
                this,
                HomeViewModelFactory(HomeRepository(retrofitService))
            )[HomeViewModel::class.java]

        findViewById<ComposeView>(binding.homeViewDisplay.id)
            .setContent {
                Scaffold(
                    backgroundColor= Color(resources.getColor(R.color.dark_blue_grey)),
                    topBar = {
                        val searchWidgetState by viewModel.searchWidgetState
                        val searchTextState by viewModel.searchTextState
                        MainAppBar(
                            searchWidgetState = searchWidgetState,
                            productViewModel = viewModel,
                            searchTextState = searchTextState,

                            onTextChange = {
                                viewModel.updateSearchTextState(newValue = it)
                                viewModel.getSearchMovies(searchText = it)
                            },
                            onCloseClicked = {
                                viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                                viewModel.getHomeScreenMoviesAndSeries()
                            },
                            onSearchClicked = {
                                viewModel.getSearchMovies(searchText = it)
                            },
                        ) {
                            viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        }
                    },
                    modifier = Modifier.fillMaxSize(),

                    content = { padding ->
                        if(initialCall) {
                            viewModel.getHomeScreenMoviesAndSeries()
                            initialCall = false
                        }
                        val content = viewModel.movieList.collectAsState()

                        when (content.value.state) {
                            State.Loading -> {}
                            State.Searched -> {
                                Column(
                                    modifier = Modifier
                                        .verticalScroll(rememberScrollState())
                                        .fillMaxSize()
                                        .padding(padding),
                                ) {
                                    val context = LocalContext.current
                                    if(content.value.seriesSelection.isNotEmpty()) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Spacer(
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        Text(
                                            "Movies",
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 20.sp,
                                            color = Color.White,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(modifier = Modifier
                                            .clickable {
                                                val intent =
                                                    Intent(
                                                        context,
                                                        ViewMoreActivity::class.java
                                                    )
                                                intent.putExtra(
                                                    "category",
                                                    CategoryEntry.SearchedMovies
                                                )
                                                intent.putExtra("searchedQuery", searchedQuery)
                                                intent.putExtra("isMovie", true)
                                                context.startActivity(intent)
                                            }
                                            .weight(0.1f),
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = "Forward",
                                            tint = Color.White)
                                    }
                                    Spacer(
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    LazyVerticalGrid(modifier = Modifier.heightIn(max= 800.dp), columns = GridCells.Fixed(4), userScrollEnabled = false, content = {
                                        items(content.value.movieSelection.size) { index ->
                                            Card(
                                                elevation = 4.dp,
                                                backgroundColor = Color.Transparent,
                                                modifier = Modifier.height(150.dp).clickable {
                                                    val intent =
                                                        Intent(context, InfoActivity::class.java)
                                                    intent.putExtra("id", content.value.movieSelection[index].id)
                                                    intent.putExtra("isMovie", true)
                                                    context.startActivity(intent)
                                                },
                                                content = {
                                                    Row(
                                                        modifier = Modifier.fillMaxSize(),
                                                        content = {
                                                            if (content.value.movieSelection[index].poster != null) {
                                                                AsyncImage(
                                                                    contentScale = ContentScale.FillBounds,
                                                                    modifier = Modifier
                                                                        .fillMaxSize(),
                                                                    alignment = Alignment.Center,
                                                                    model = "https://image.tmdb.org/t/p/w300" + content.value.movieSelection[index].poster,
                                                                    contentDescription = null
                                                                )
                                                            } else Image(modifier = Modifier.fillMaxSize(), alignment = Alignment.Center, painter = painterResource(R.drawable.ic_baseline_question_mark_24),contentDescription = "")
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    })
                                    }
                                    Spacer(
                                        modifier = Modifier.padding(20.dp)
                                    )
                                    if(content.value.productionTeam.isNotEmpty()) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Spacer(
                                                modifier = Modifier.padding(10.dp)
                                            )
                                            Text(
                                                "People",
                                                fontFamily = fonts,
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 20.sp,
                                                color = Color.White,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Icon(modifier = Modifier
                                                .clickable {
                                                    val intent =
                                                        Intent(
                                                            context,
                                                            ViewMoreActivity::class.java
                                                        )
                                                    intent.putExtra(
                                                        "category",
                                                        CategoryEntry.PopularMovies
                                                    )
                                                    intent.putExtra("isMovie", true)
                                                    context.startActivity(intent)
                                                }
                                                .weight(0.1f),
                                                imageVector = Icons.Default.ArrowForward,
                                                contentDescription = "Forward",
                                                tint = Color.White)
                                        }
                                        Spacer(
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        LazyRow {
                                            itemsIndexed(content.value.productionTeam) { _, cast ->
                                                Card(
                                                    Modifier.width(110.dp).clickable {

                                                    },
                                                    backgroundColor = Color.Transparent,
                                                    elevation = 1.dp,
                                                    content = {
                                                                Column(
                                                                    modifier = Modifier.height(200.dp),
                                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                                    content = {
                                                                        if(cast.personImgPath != null) {
                                                                            AsyncImage(
                                                                                modifier = Modifier.height(160.dp).fillMaxWidth(),
                                                                                contentScale = ContentScale.FillBounds,
                                                                                alignment = Alignment.TopCenter,
                                                                                model = "https://image.tmdb.org/t/p/w300" + cast.personImgPath,
                                                                                contentDescription = stringResource(
                                                                                    R.string.app_name
                                                                                )
                                                                            )
                                                                        } else Image(modifier = Modifier.height(160.dp), alignment = Alignment.Center, painter = painterResource(R.drawable.ic_baseline_question_mark_24),contentDescription = "")

                                                                        Text(
                                                                                modifier = Modifier.width(100.dp),
                                                                                text = cast.name,
                                                                                textAlign = TextAlign.Center,
                                                                                fontFamily = fonts,
                                                                                fontWeight = FontWeight.Normal,
                                                                                fontSize = 15.sp,
                                                                                color = Color.White,
                                                                            )

                                                                    }
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }


                                    Spacer(
                                        modifier = Modifier.padding(20.dp)
                                    )

                                    if(content.value.seriesSelection.isNotEmpty()) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Spacer(
                                                modifier = Modifier.padding(10.dp)
                                            )
                                            Text(
                                                "Tv Shows",
                                                fontFamily = fonts,
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 20.sp,
                                                color = Color.White,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Icon(modifier = Modifier
                                                .clickable {
                                                    val intent =
                                                        Intent(
                                                            context,
                                                            ViewMoreActivity::class.java
                                                        )
                                                    intent.putExtra(
                                                        "category",
                                                        CategoryEntry.PopularMovies
                                                    )
                                                    intent.putExtra("isMovie", true)
                                                    context.startActivity(intent)
                                                }
                                                .weight(0.1f),
                                                imageVector = Icons.Default.ArrowForward,
                                                contentDescription = "Forward",
                                                tint = Color.White)
                                        }
                                        Spacer(
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        LazyVerticalGrid(
                                            modifier = Modifier.heightIn(max= 800.dp),
                                            columns = GridCells.Fixed(4),
                                            userScrollEnabled = false,
                                            content = {
                                                items(content.value.seriesSelection.size) { index ->
                                                    Card(
                                                        elevation = 4.dp,
                                                        backgroundColor = Color.Transparent,
                                                        modifier = Modifier.height(150.dp).clickable {
                                                        val intent =
                                                            Intent(context, InfoActivity::class.java)
                                                        intent.putExtra("id", content.value.seriesSelection[index].id)
                                                        intent.putExtra("isMovie", false)
                                                        context.startActivity(intent)
                                                    },
                                                        content = {
                                                            Row(
                                                                modifier = Modifier.fillMaxSize(),
                                                                content = {
                                                                    if(content.value.seriesSelection[index].poster != null){
                                                                    AsyncImage(
                                                                        contentScale = ContentScale.FillBounds,
                                                                        modifier = Modifier
                                                                            .fillMaxSize(),
                                                                        alignment = Alignment.Center,
                                                                        model = "https://image.tmdb.org/t/p/w300" + content.value.seriesSelection[index].poster,
                                                                        contentDescription = null
                                                                    )
                                                                }
                                                                else Image(modifier = Modifier.fillMaxSize(), alignment = Alignment.Center, painter = painterResource(R.drawable.ic_baseline_question_mark_24),contentDescription = "")
                                                                }
                                                            )
                                                        }
                                                    )
                                                }
                                            })
                                    }
                                }

                            }
                            State.Success ->
                                Column(
                                    modifier = Modifier
                                        .verticalScroll(rememberScrollState())
                                        .fillMaxSize()
                                        .padding(padding),
                                ) {
                                    val context = LocalContext.current
                                    Row(
                                        modifier = Modifier.fillMaxHeight(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            "Popular Movies",
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 20.sp,
                                            color = Color.White,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(modifier = Modifier
                                            .clickable {
                                                val intent =
                                                    Intent(
                                                        context,
                                                        ViewMoreActivity::class.java
                                                    )
                                                intent.putExtra(
                                                    "category",
                                                    CategoryEntry.PopularMovies
                                                )
                                                intent.putExtra("isMovie", true)
                                                context.startActivity(intent)
                                            }
                                            .weight(0.1f),
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = "Forward",
                                            tint = Color.White)
                                    }

                                    Spacer(
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    LazyRow {
                                        itemsIndexed(content.value.movieSelection) { _, movieSelected ->
                                            Card(
                                                elevation = 4.dp,
                                                backgroundColor = Color.Transparent,
                                                content = {
                                                    val context = LocalContext.current
                                                    Row(
                                                        modifier = Modifier
                                                            .clickable {
                                                                val intent =
                                                                    Intent(
                                                                        context,
                                                                        InfoActivity::class.java
                                                                    )
                                                                intent.putExtra(
                                                                    "id",
                                                                    movieSelected.id
                                                                )
                                                                intent.putExtra("isMovie", true)
                                                                context.startActivity(intent)
                                                            }
                                                            .fillMaxWidth(), content = {
                                                            Row(
                                                                content = {
                                                                    AsyncImage(
                                                                        contentScale = ContentScale.FillBounds,
                                                                        modifier = Modifier
                                                                            .fillMaxSize(),
                                                                        alignment = Alignment.Center,
                                                                        model = "https://image.tmdb.org/t/p/w300" + movieSelected.poster,
                                                                        contentDescription = stringResource(
                                                                            R.string.app_name
                                                                        )
                                                                    )
                                                                }
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }
                                    Spacer(
                                        modifier = Modifier.padding(15.dp)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxHeight(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            "Popular Tv Shows",
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 20.sp,
                                            color = Color.White,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(modifier = Modifier
                                            .clickable {
                                                val intent =
                                                    Intent(
                                                        context,
                                                        ViewMoreActivity::class.java
                                                    )
                                                intent.putExtra(
                                                    "category",
                                                    CategoryEntry.PopularTvShows
                                                )
                                                intent.putExtra("isMovie", false)
                                                context.startActivity(intent)
                                            }
                                            .weight(0.1f),
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = "Forward",
                                            tint = Color.White)
                                    }
                                    Spacer(
                                        modifier = Modifier.padding(10.dp)
                                    )

                                    LazyRow {
                                        itemsIndexed(content.value.seriesSelection) { _, seriesSelected ->
                                            Card(
                                                elevation = 4.dp,
                                                backgroundColor = Color.Transparent,
                                                content = {
                                                    val context = LocalContext.current
                                                    Row(
                                                        modifier = Modifier
                                                            .clickable {
                                                                val intent =
                                                                    Intent(
                                                                        context,
                                                                        InfoActivity::class.java
                                                                    )
                                                                intent.putExtra(
                                                                    "id",
                                                                    seriesSelected.id
                                                                )
                                                                intent.putExtra("isMovie", false)
                                                                context.startActivity(intent)
                                                            }
                                                            .fillMaxWidth(), content = {
                                                            Row(
                                                                content = {
                                                                    AsyncImage(
                                                                        contentScale = ContentScale.FillBounds,
                                                                        modifier = Modifier
                                                                            .fillMaxSize(),
                                                                        alignment = Alignment.Center,
                                                                        model = "https://image.tmdb.org/t/p/w300" + seriesSelected.poster,
                                                                        contentDescription = stringResource(
                                                                            R.string.app_name
                                                                        )
                                                                    )
                                                                }
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }

                                    Spacer(
                                        modifier = Modifier.padding(15.dp)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxHeight(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            "On Theatres",
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 20.sp,
                                            color = Color.White,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(modifier = Modifier
                                            .clickable {
                                                val intent =
                                                    Intent(
                                                        context,
                                                        ViewMoreActivity::class.java
                                                    )
                                                intent.putExtra(
                                                    "category",
                                                    CategoryEntry.OnTheatres
                                                )
                                                intent.putExtra("isMovie", true)
                                                context.startActivity(intent)
                                            }
                                            .weight(0.1f),
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = "Forward",
                                            tint = Color.White)
                                    }
                                    Spacer(
                                        modifier = Modifier.padding(10.dp)
                                    )

                                    LazyRow {
                                        itemsIndexed(content.value.onTheatres) { _, movieSelected ->
                                            Card(
                                                elevation = 4.dp,
                                                backgroundColor = Color.Transparent,
                                                content = {
                                                    val context = LocalContext.current
                                                    Row(
                                                        modifier = Modifier
                                                            .clickable {
                                                                val intent =
                                                                    Intent(
                                                                        context,
                                                                        InfoActivity::class.java
                                                                    )
                                                                intent.putExtra(
                                                                    "id",
                                                                    movieSelected.id
                                                                )
                                                                intent.putExtra("isMovie", true)
                                                                context.startActivity(intent)
                                                            }
                                                            .fillMaxWidth(), content = {
                                                            Row(
                                                                content = {
                                                                    AsyncImage(
                                                                        contentScale = ContentScale.FillBounds,
                                                                        modifier = Modifier
                                                                            .fillMaxSize(),
                                                                        alignment = Alignment.Center,
                                                                        model = "https://image.tmdb.org/t/p/w300" + movieSelected.poster,
                                                                        contentDescription = stringResource(
                                                                            R.string.app_name
                                                                        )
                                                                    )
                                                                }
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }

                                    Spacer(
                                        modifier = Modifier.padding(15.dp)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxHeight(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            "Upcoming Movies",
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 20.sp,
                                            color = Color.White,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(modifier = Modifier
                                            .clickable {
                                                val intent =
                                                    Intent(
                                                        context,
                                                        ViewMoreActivity::class.java
                                                    )
                                                intent.putExtra(
                                                    "category",
                                                    CategoryEntry.UpcomingMovies
                                                )
                                                intent.putExtra("isMovie", true)
                                                context.startActivity(intent)
                                            }
                                            .weight(0.1f),
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = "Forward",
                                            tint = Color.White)
                                    }
                                    Spacer(
                                        modifier = Modifier.padding(10.dp)
                                    )

                                    LazyRow {
                                        itemsIndexed(content.value.upcomingMoviesSelection) { _, movieSelected ->
                                            Card(
                                                elevation = 4.dp,
                                                backgroundColor = Color.Transparent,
                                                content = {
                                                    val context = LocalContext.current
                                                    Row(
                                                        modifier = Modifier
                                                            .clickable {
                                                                val intent =
                                                                    Intent(
                                                                        context,
                                                                        InfoActivity::class.java
                                                                    )
                                                                intent.putExtra(
                                                                    "id",
                                                                    movieSelected.id
                                                                )
                                                                intent.putExtra("isMovie", true)
                                                                context.startActivity(intent)
                                                            }
                                                            .fillMaxWidth(), content = {
                                                            Row(
                                                                content = {
                                                                    AsyncImage(
                                                                        contentScale = ContentScale.FillBounds,
                                                                        modifier = Modifier
                                                                            .fillMaxSize(),
                                                                        alignment = Alignment.Center,
                                                                        model = "https://image.tmdb.org/t/p/w300" + movieSelected.poster,
                                                                        contentDescription = stringResource(
                                                                            R.string.app_name
                                                                        )
                                                                    )
                                                                }
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }

                                    Spacer(
                                        modifier = Modifier.padding(15.dp)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxHeight(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            "Shows Currently Airing",
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 20.sp,
                                            color = Color.White,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(modifier = Modifier
                                            .clickable {
                                                val intent =
                                                    Intent(
                                                        context,
                                                        ViewMoreActivity::class.java
                                                    )
                                                intent.putExtra(
                                                    "category",
                                                    CategoryEntry.ShowsCurrentlyAiring
                                                )
                                                intent.putExtra("isMovie", false)
                                                context.startActivity(intent)
                                            }
                                            .weight(0.1f),
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = "Forward",
                                            tint = Color.White)
                                    }
                                    Spacer(
                                        modifier = Modifier.padding(10.dp)
                                    )

                                    LazyRow {
                                        itemsIndexed(content.value.upcomingSeriesSelection) { _, seriesSelected ->
                                            Card(
                                                elevation = 4.dp,
                                                backgroundColor = Color.Transparent,
                                                content = {
                                                    val context = LocalContext.current
                                                    Row(
                                                        modifier = Modifier
                                                            .clickable {
                                                                val intent =
                                                                    Intent(
                                                                        context,
                                                                        InfoActivity::class.java
                                                                    )
                                                                intent.putExtra(
                                                                    "id",
                                                                    seriesSelected.id
                                                                )
                                                                intent.putExtra("isMovie", false)
                                                                context.startActivity(intent)
                                                            }
                                                            .fillMaxWidth(), content = {
                                                            Row(
                                                                content = {
                                                                    AsyncImage(
                                                                        contentScale = ContentScale.FillBounds,
                                                                        modifier = Modifier
                                                                            .fillMaxSize(),
                                                                        alignment = Alignment.Center,
                                                                        model = "https://image.tmdb.org/t/p/w300" + seriesSelected.poster,
                                                                        contentDescription = stringResource(
                                                                            R.string.app_name
                                                                        )
                                                                    )
                                                                }
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                        }
                    }
                )
            }
    }

    @Composable
    fun DefaultAppBar(
        onSearchClicked: () -> Unit,
    ) {

        TopAppBar(
            backgroundColor = Color(resources.getColor(R.color.dark_blue_grey)),

            title = {
                Text(
                    text = "Binwatch",
                    color = Color.White
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        onSearchClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            }
        )
    }

    @Composable
    fun MainAppBar(
        searchWidgetState: SearchWidgetState,
        productViewModel: HomeViewModel,
        searchTextState: String,
        onTextChange: (String) -> Unit,
        onCloseClicked: () -> Unit,
        onSearchClicked: (String) -> Unit,
        onSearchTriggered: () -> Unit,
    ) {
        when (searchWidgetState) {
            SearchWidgetState.CLOSED -> {
                DefaultAppBar(
                    onSearchClicked = onSearchTriggered,
                )
            }
            SearchWidgetState.OPENED -> {
                SearchAppBar(
                    text = searchTextState,
                    onTextChange = onTextChange,
                    onCloseClicked = onCloseClicked,
                    onSearchClicked = onSearchClicked,
                    productViewModel = productViewModel
                )
            }
        }

    }

    @Composable
    fun SearchAppBar(
        text: String,
        onTextChange: (String) -> Unit,
        onCloseClicked: () -> Unit,
        onSearchClicked: (String) -> Unit,
        productViewModel: HomeViewModel
    ) {
        var search = false
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            elevation = AppBarDefaults.TopAppBarElevation,
            color = Color(resources.getColor(R.color.dark_blue_grey))
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = text,
                onValueChange = {
                    searchedQuery = it
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        text = "Search here...",
                        color = Color.White
                    )
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.White
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                                search = false
                            }
                            productViewModel.closeSearchBar()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = Color.White
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        search = true
                        onSearchClicked(text)
                    }
                ),
            )
        }
    }
}



