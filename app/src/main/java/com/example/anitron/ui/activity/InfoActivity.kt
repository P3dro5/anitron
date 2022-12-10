package com.example.anitron.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberAsyncImagePainter
import com.example.anitron.R
import com.example.anitron.data.datasource.movieInfo.MovieInfo
import com.example.anitron.data.datasource.State
import com.example.anitron.data.datasource.tvshowInfo.TvShowInfo
import com.example.anitron.data.repository.MovieInfoRepository
import com.example.anitron.databinding.MovieInfoBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.InfoViewModelFactory
import com.example.anitron.ui.theme.fonts
import com.example.anitron.ui.viewmodel.InfoViewModel

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: MovieInfoBinding

    lateinit var viewModel: InfoViewModel

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MovieInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieTvShowId = intent.getStringExtra("id")
        val isMovie = intent.getBooleanExtra("isMovie", false)

        viewModel = ViewModelProvider(
            this,
            InfoViewModelFactory(MovieInfoRepository(retrofitService))
        )[InfoViewModel::class.java]
        findViewById<ComposeView>(binding.infoViewDisplay.id)
            .setContent {
                if (isMovie) {
                    if (movieTvShowId != null) {
                        viewModel.getMovieOnClick(movieTvShowId)
                    }
                } else {
                    if (movieTvShowId != null) {
                        try {
                            viewModel.getShowOnClick(movieTvShowId)
                        }catch (e: Exception){
                            Log.d("A "+ e.message,"WTFFFF")
                        }
                    }
                }
                val tvShow = viewModel.tvShowInfo.collectAsState()
                val movie = viewModel.movieInfo.collectAsState()
                if(isMovie) {
                    when (movie.value.state) {
                        State.Loading -> {}
                        State.Success ->
                            movie.value.movie?.let { MovieInfoScreen(movieInfo = it) }
                        else -> {}
                    }
                }
                else{
                    when (tvShow.value.state) {
                        State.Loading -> {}
                        State.Success ->
                            tvShow.value.tvShow?.let { TvShowInfoScreen(tvShowInfo = it) }
                        else -> {}
                    }
                }
            }
    }

    @Composable
    private fun TvShowInfoScreen(tvShowInfo: TvShowInfo) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                backgroundColor = Color(resources.getColor(R.color.dark_blue_grey)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500" + tvShowInfo.posterPath),
                        contentDescription = null,
                        modifier = Modifier.size(328.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                tvShowInfo.name,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "Score:" + String.format(
                                    "%.2f",
                                    tvShowInfo.voteAverage.toDouble()
                                ) + "/10",
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.weight(0.25f),
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(10.dp),

                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        Text(
                            tvShowInfo.overview,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Column(
                            modifier = Modifier.animateContentSize()
                        ) {
                            Text(
                                "Genres:",
                                fontFamily = fonts,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            ) {
                                items(tvShowInfo.genres) { genre ->
                                    genre.name?.let {
                                        Text(
                                            it,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 15.sp,
                                            color = Color.White,
                                            modifier = Modifier.weight(weight = 1f)
                                        )
                                    }

                                }
                            }

                            Spacer(modifier = Modifier.padding(15.dp))
                            Text(
                                "Created by:",
                                fontFamily = fonts,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                            LazyRow(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            ) {
                                items(tvShowInfo.createdBy) { creator ->
                                    Text(
                                        creator.name.toString(),
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 15.sp,
                                        color = Color.White,
                                        modifier = Modifier.weight(weight = 1f)
                                    )
                                }
                            }

                        }
                        Column(
                            modifier = Modifier.animateContentSize()
                        ) {
                            Text(
                                "Producers:",
                                fontFamily = fonts,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            ) {
                                items(tvShowInfo.productionCompanies) { genre ->
                                    genre.name?.let {
                                        Text(
                                            it,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 15.sp,
                                            color = Color.White,
                                            modifier = Modifier.weight(weight = 1f)
                                        )
                                    }

                                }
                            }
                        }

                        Column(
                            modifier = Modifier.animateContentSize()
                        ) {
                            Text(
                                "Last episode to air: ",
                                fontFamily = fonts,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 17.sp,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.padding(5.dp))

                            tvShowInfo.lastEpisodeToAir?.name?.let {
                                Text(
                                    it,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.padding(15.dp))

                            Text(
                                "Air date: " + tvShowInfo.lastEpisodeToAir?.airDate,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.padding(15.dp))
                            Text(
                                "Season " + tvShowInfo.lastEpisodeToAir?.seasonNumber + ", Episode " + tvShowInfo.lastEpisodeToAir?.episodeNumber,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.padding(15.dp))
                            Text(
                                "Episode description: " + tvShowInfo.lastEpisodeToAir?.overview,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                color = Color.White
                            )


                        }

                    }
                }

            }
        }
    }

    @Composable
    private fun MovieInfoScreen(movieInfo: MovieInfo) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                backgroundColor = Color(resources.getColor(R.color.dark_blue_grey)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500" + movieInfo.posterPath),
                        contentDescription = null,
                        modifier = Modifier.size(328.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                movieInfo.title,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "Score:" + String.format(
                                    "%.2f",
                                    movieInfo.voteAverage.toDouble()
                                ) + "/10",
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.weight(0.25f),
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Runtime: " + movieInfo.runtime + " min",
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f),
                            color = Color.White
                        )
                        Text(
                            "Released: " + movieInfo.releaseDate,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            modifier = Modifier.weight(0.4f),
                            color = Color.White
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(10.dp),

                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        Text(
                            movieInfo.overview,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Column(
                            modifier = Modifier.animateContentSize()
                        ) {
                            Text(
                                "Genres:",
                                fontFamily = fonts,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            ) {
                                items(movieInfo.genres) { genre ->
                                    genre.name?.let {
                                        Text(
                                            it,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 15.sp,
                                            color = Color.White,
                                            modifier = Modifier.weight(weight = 1f)
                                        )
                                    }

                                }
                            }

                        }
                        Column(
                            modifier = Modifier.animateContentSize()
                        ) {
                            Text(
                                "Producers:",
                                fontFamily = fonts,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            ) {
                                items(movieInfo.productionCompanies) { genre ->
                                    genre.name?.let {
                                        Text(
                                            it,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 15.sp,
                                            color = Color.White,
                                            modifier = Modifier.weight(weight = 1f)
                                        )
                                    }

                                }
                            }
                        }
                    }

                }
            }

        }
    }
}
