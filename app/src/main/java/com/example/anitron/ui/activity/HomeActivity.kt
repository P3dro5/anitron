package com.example.anitron.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.anitron.R
import com.example.anitron.data.datasource.CategoryEntry
import com.example.anitron.data.datasource.SearchWidgetState
import com.example.anitron.ui.modelfactory.HomeViewModelFactory
import com.example.anitron.databinding.ActivityMainBinding
import com.example.anitron.data.repository.HomeRepository
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.theme.fonts
import com.example.anitron.ui.viewmodel.HomeViewModel
import com.example.anitron.data.datasource.State


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: HomeViewModel

    private val retrofitService = RetrofitService.getInstance()

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
                        val movie = viewModel.movieList.collectAsState()

                        when (movie.value.state) {
                            State.Loading -> {}
                            State.Searched -> {
                                val i = 0
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
                                        itemsIndexed(movie.value.movieSelection) { _, movieSelected ->
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
                                        itemsIndexed(movie.value.seriesSelection) { _, seriesSelected ->
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
                                        itemsIndexed(movie.value.onTheatres) { _, movieSelected ->
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
                                        itemsIndexed(movie.value.upcomingMoviesSelection) { _, movieSelected ->
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
                                        itemsIndexed(movie.value.upcomingSeriesSelection) { _, seriesSelected ->
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



