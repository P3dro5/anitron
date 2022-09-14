package com.example.anitron.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberAsyncImagePainter
import com.example.anitron.R
import com.example.anitron.data.datasource.MovieInfo
import com.example.anitron.databinding.MovieInfoBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.MovieInfoViewModelFactory
import com.example.anitron.data.repository.MovieInfoRepository
import com.example.anitron.ui.theme.fonts
import com.example.anitron.ui.viewmodel.MovieInfoViewModel

class MovieInfoActivity : AppCompatActivity() {
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var binding: MovieInfoBinding

    lateinit var viewModel: MovieInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieInfoBinding.inflate(layoutInflater)

        val prevIntent = intent
        val imdbId = prevIntent.getStringExtra("imdbId")

        //get viewModel instance using MyViewModelFactory
        viewModel =
            ViewModelProvider(
                this,
                MovieInfoViewModelFactory(MovieInfoRepository(retrofitService))
            ).get(
                MovieInfoViewModel::class.java
            )
        if (imdbId != null) {
            viewModel.getMovieOnClick(imdbId)
        }

        viewModel.movieInfo.observe(this) {
            setContent {
                MovieInfoScreen(it)
            }
        }
    }

    @Composable
    private fun MovieInfoScreen(movieInfo: MovieInfo) {
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
                    painter = rememberAsyncImagePainter(movieInfo.poster),
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
                            "Score:" + movieInfo.imdbRating + "/10",
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
                        "Released: " + movieInfo.year,
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
                        movieInfo.plot,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Text(
                        "Genre: " + movieInfo.genre,
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
                        "Actors: " + movieInfo.actors,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Text(
                        "Director: " + movieInfo.director,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,

                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Text(
                        "BoxOffice: " + movieInfo.boxOffice,
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
                        "Language: " + movieInfo.language,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "Country: " + movieInfo.country,
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