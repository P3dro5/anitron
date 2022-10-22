package com.example.anitron.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.example.anitron.R
import com.example.anitron.data.datasource.Movie
import com.example.anitron.ui.modelfactory.HomeViewModelFactory
import com.example.anitron.ui.adapter.MovieRecyclerAdapter
import com.example.anitron.databinding.ActivityMainBinding
import com.example.anitron.data.repository.HomeRepository
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.viewmodel.HomeViewModel
import com.example.anitron.ui.viewmodel.State


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: HomeViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = MovieRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get viewmodel instance using MyViewModelFactory
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(HomeRepository(retrofitService))).get(
                HomeViewModel::class.java
            )
        binding.recycler.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)
        }
        //set recyclerview adapter
        binding.recycler.adapter = adapter

        /*viewModel.movieList.observe(this, Observer {
            Log.d(TAG, "movieList: $it")
            adapter.setMovieList(it)
        })*/

        /*viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
        })*/

        //viewModel.getAllMovies()
        //viewModel.movieList.observe(this) {

        findViewById<ComposeView>(binding.searchBar.id)
            .setContent {
                SearchMovieGrid(movieViewModel = viewModel) {}
            }
    }

}

@Composable
private fun SearchMovieGrid(
    movieViewModel: HomeViewModel,
    movieNavigation: (Movie) -> Unit
) {
    val movie = movieViewModel.movieList.collectAsState()
    HomeSearchBar(
        hint = "Search...",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onSearch = {
            for (movie in movieViewModel.movieList.value.movieSelection) {
                if (it == movie.title) {
                    val movieList = listOf(movie)
                    movieViewModel.searchMovies(movieList)
                }
            }
            movieViewModel.getSearchMovies(it)
        }
    )
    Spacer(modifier = Modifier.height(200.dp))
    when (movie.value.state) {
        State.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Pesquisa por produtos clicando no icone de pesquisa")
            }
        }
        State.Failed -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Não foram encontrados resultados")
            }
        }
        State.Success -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(4), content = {
                    items(movie.value.movieSelection) { movieSelected ->
                        Card(
                            elevation = 4.dp,
                            modifier = Modifier.fillMaxWidth().height(150.dp),
                            content = {
                                Row(
                                    modifier = Modifier
                                        .clickable {


                                        }
                                        .fillMaxWidth(), content = {
                                        Column(
                                            content = {
                                                AsyncImage(
                                                    contentScale = ContentScale.FillBounds,
                                                    modifier = Modifier.fillMaxSize().size(200.dp),
                                                    alignment = Alignment.Center,
                                                    model = movieSelected.poster,
                                                    contentDescription = stringResource(R.string.app_name)
                                                )
                                            }
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
                )
            }
        }
    }
}

@Composable
private fun HomeSearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
    hint: String = ""
) {
    var searchText by remember {
        mutableStateOf("")
    }

    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(26.dp))
                .clip(AbsoluteRoundedCornerShape(20.dp)),
            value = searchText,
            placeholder = {
                Text(text = hint)
            },
            onValueChange = {
                searchText = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.DarkGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}




