package com.example.anitron.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.example.anitron.data.datasource.MovieInfo
import com.example.anitron.data.datasource.State
import com.example.anitron.data.repository.MovieInfoRepository
import com.example.anitron.databinding.MovieInfoBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.InfoViewModelFactory
import com.example.anitron.ui.theme.fonts
import com.example.anitron.ui.viewmodel.InfoViewModel

class InfoActivity : AppCompatActivity(){

    private lateinit var binding: MovieInfoBinding

    lateinit var viewModel: InfoViewModel

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MovieInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieTvShowId = intent.getStringExtra("id")
        val isMovie = intent.getBooleanExtra("isMovie", false)

        viewModel = ViewModelProvider(this, InfoViewModelFactory(MovieInfoRepository(retrofitService)))[InfoViewModel::class.java]

        if(isMovie) {
            if (movieTvShowId != null) {
                viewModel.getMovieOnClick(movieTvShowId)
            }
        }
        else{
            if (movieTvShowId != null) {
                viewModel.getShowOnClick(movieTvShowId)
            }
        }
        findViewById<ComposeView>(binding.infoViewDisplay.id)
            .setContent {
                if(isMovie){
                    if (movieTvShowId != null) {
                        viewModel.getMovieOnClick(movieTvShowId)
                    }
                }
                else{
                    if (movieTvShowId != null) {
                        viewModel.getShowOnClick(movieTvShowId)
                    }
                }
                val tvShowMovie = viewModel.movieTvShowInfo.collectAsState()
                when(tvShowMovie.value.state){
                    State.Loading -> {}
                    State.Success ->
                        tvShowMovie.value.movieTvShow?.let { MovieTvShowInfoScreen(movieInfo = it) }
                }
            }
    }

    @Composable
    private fun MovieTvShowInfoScreen(movieInfo: MovieInfo) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                backgroundColor = Color(resources.getColor(R.color.dark_blue_grey)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .width(IntrinsicSize.Max)

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
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500"+ movieInfo.posterPath),
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
                                "Score:" + movieInfo.voteAverage + "/10",
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
                            "Runtime: " + movieInfo.runtime,
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
                            modifier = Modifier.weight(1f),
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
                        Text(
                            "Genre: " + movieInfo.genres,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Text(
                            "Actors: " + movieInfo.released,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Text(
                            "Director: " + movieInfo.released,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,

                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Text(
                            "BoxOffice: " + movieInfo.released,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Language: " + movieInfo.released,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            "Country: " + movieInfo.released,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.weight(1f),
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                }
            }


        }
    }

}