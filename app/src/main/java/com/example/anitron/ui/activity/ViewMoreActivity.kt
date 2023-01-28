package com.example.anitron.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.anitron.R
import com.example.anitron.data.datasource.CategoryEntry
import com.example.anitron.data.datasource.State
import com.example.anitron.data.repository.HomeRepository
import com.example.anitron.databinding.ViewMoreBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.ViewMoreViewModelFactory
import com.example.anitron.ui.viewmodel.ViewMoreViewModel

class ViewMoreActivity : AppCompatActivity() {

    private lateinit var binding: ViewMoreBinding

    lateinit var viewModel: ViewMoreViewModel

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ViewMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val category = intent.getSerializableExtra("category")
        val isMovie = intent.getBooleanExtra("isMovie", false)
        val searchedQuery = intent.getStringExtra("searchedQuery")

        viewModel = ViewModelProvider(
            this,
            ViewMoreViewModelFactory(HomeRepository(retrofitService))
        )[ViewMoreViewModel::class.java]

        findViewById<ComposeView>(binding.viewMoreScreenDisplay.id)
            .setContent {
                val entries = viewModel.entries.collectAsState()
                when(category){
                    CategoryEntry.PopularMovies -> {
                        viewModel.getPopularMovieList()
                    }
                    CategoryEntry.PopularTvShows -> {
                        viewModel.getPopularTvShowList()
                    }
                    CategoryEntry.OnTheatres -> {
                        viewModel.getOnTheatresList()
                    }
                    CategoryEntry.UpcomingMovies -> {
                        viewModel.getUpcomingMoviesList()
                    }
                    CategoryEntry.ShowsCurrentlyAiring -> {
                        viewModel.getShowsCurrentlyAiringList()
                    }
                    CategoryEntry.SearchedMovies -> {
                        if (searchedQuery != null) {
                            viewModel.getSearchedMoviesList(searchedQuery)
                        }
                    }
                    CategoryEntry.SearchedTvShows -> {
                        if (searchedQuery != null) {
                            viewModel.getSearchedTvShowList(searchedQuery)
                        }
                    }

                }

                when (entries.value.state) {
                    State.Loading -> {}
                    State.Success -> {
                        LazyVerticalGrid(columns = GridCells.Fixed(3), content = {
                            items(entries.value.movies.size) { index ->
                                val context = LocalContext.current
                                Row(
                                    modifier = Modifier.height(200.dp)
                                        .clickable {
                                            val intent =
                                                Intent(context, InfoActivity::class.java)
                                            intent.putExtra("id", entries.value.movies[index].id)
                                            intent.putExtra("isMovie", isMovie)
                                            context.startActivity(intent)
                                        }
                                        .fillMaxWidth(), content = {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            content = {
                                                if (entries.value.movies[index].poster != null) {
                                                    AsyncImage(
                                                        contentScale = ContentScale.FillBounds,
                                                        modifier = Modifier
                                                            .fillMaxSize(),
                                                        alignment = Alignment.Center,
                                                        model = "https://image.tmdb.org/t/p/w500" + entries.value.movies[index].poster,
                                                        contentDescription = stringResource(R.string.app_name)
                                                    )
                                                }
                                                else Image(modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillWidth, alignment = Alignment.Center, painter = painterResource(R.drawable.ic_baseline_question_mark_24),contentDescription = "")
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
}