package com.example.anitron.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.anitron.R
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

    private var isMovie : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get viewmodel instance using MyViewModelFactory
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(HomeRepository(retrofitService)))[HomeViewModel::class.java]

        findViewById<ComposeView>(binding.homeViewDisplay.id)
            .setContent {
                viewModel.getHomeScreenMoviesAndSeries()
                val movie = viewModel.movieList.collectAsState()

                when (movie.value.state) {
                    State.Loading -> {}
                    State.Success ->
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .padding(15.dp),
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
                        Icon(modifier = Modifier.clickable {
                            val intent = Intent(context, ViewMoreActivity::class.java)
                            context.startActivity(intent)
                        }.weight(0.1f), imageVector = Icons.Default.ArrowForward, contentDescription = "Forward", tint = Color.White)
                    }

                    Spacer(
                        modifier = Modifier.padding(10.dp)
                    )
                    LazyRow{
                        itemsIndexed(movie.value.movieSelection) { _, movieSelected ->
                            Card(
                                elevation = 4.dp,
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(170.dp),
                                backgroundColor = Color.Transparent,
                                content = {
                                    val context = LocalContext.current
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                isMovie = true
                                                val intent =
                                                    Intent(context, InfoActivity::class.java)
                                                intent.putExtra("id", movieSelected.id)
                                                intent.putExtra("isMovie", isMovie)
                                                context.startActivity(intent)
                                            }
                                            .fillMaxWidth(), content = {
                                            Row(
                                                content = {
                                                    AsyncImage(
                                                        contentScale = ContentScale.FillBounds,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .size(200.dp),
                                                        alignment = Alignment.Center,
                                                        model = "https://image.tmdb.org/t/p/w500" + movieSelected.poster,
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
                        Icon(modifier = Modifier.clickable {
                            val intent = Intent(context, ViewMoreActivity::class.java)
                            context.startActivity(intent)
                        }.weight(0.1f), imageVector = Icons.Default.ArrowForward, contentDescription = "Forward", tint = Color.White)
                    }
                    Spacer(
                        modifier = Modifier.padding(10.dp)
                    )

                    LazyRow{
                        itemsIndexed(movie.value.seriesSelection) { _, seriesSelected ->
                            Card(
                                elevation = 4.dp,
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(170.dp),
                                backgroundColor = Color.Transparent,
                                content = {
                                    val context = LocalContext.current
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                isMovie = false
                                                val intent =
                                                    Intent(context, InfoActivity::class.java)
                                                intent.putExtra("id", seriesSelected.id)
                                                intent.putExtra("isMovie", isMovie)
                                                context.startActivity(intent)
                                            }
                                            .fillMaxWidth(), content = {
                                            Row(
                                                content = {
                                                    AsyncImage(
                                                        contentScale = ContentScale.FillBounds,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .size(200.dp),
                                                        alignment = Alignment.Center,
                                                        model = "https://image.tmdb.org/t/p/w500" + seriesSelected.poster,
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
                        Icon(modifier = Modifier.clickable {
                            val intent = Intent(context, ViewMoreActivity::class.java)
                            context.startActivity(intent)
                        }.weight(0.1f), imageVector = Icons.Default.ArrowForward, contentDescription = "Forward", tint = Color.White)
                    }
                    Spacer(
                        modifier = Modifier.padding(10.dp)
                    )

                    LazyRow{
                        itemsIndexed(movie.value.onTheatres) { _, movieSelected ->
                            Card(
                                elevation = 4.dp,
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(170.dp),
                                backgroundColor = Color.Transparent,
                                content = {
                                    val context = LocalContext.current
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                isMovie = true
                                                val intent =
                                                    Intent(context, InfoActivity::class.java)
                                                intent.putExtra("id", movieSelected.id)
                                                intent.putExtra("isMovie", isMovie)
                                                context.startActivity(intent)
                                            }
                                            .fillMaxWidth(), content = {
                                            Row(
                                                content = {
                                                    AsyncImage(
                                                        contentScale = ContentScale.FillBounds,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .size(200.dp),
                                                        alignment = Alignment.Center,
                                                        model = "https://image.tmdb.org/t/p/w500" + movieSelected.poster,
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
                        Icon(modifier = Modifier.clickable {
                            val intent = Intent(context, ViewMoreActivity::class.java)
                            context.startActivity(intent)
                        }.weight(0.1f), imageVector = Icons.Default.ArrowForward, contentDescription = "Forward", tint = Color.White)
                    }
                    Spacer(
                        modifier = Modifier.padding(10.dp)
                    )

                    LazyRow{
                        itemsIndexed(movie.value.upcomingMoviesSelection) { _, movieSelected ->
                            Card(
                                elevation = 4.dp,
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(170.dp),
                                backgroundColor = Color.Transparent,
                                content = {
                                    val context = LocalContext.current
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                isMovie = true
                                                val intent =
                                                    Intent(context, InfoActivity::class.java)
                                                intent.putExtra("id", movieSelected.id)
                                                intent.putExtra("isMovie", isMovie)
                                                context.startActivity(intent)
                                            }
                                            .fillMaxWidth(), content = {
                                            Row(
                                                content = {
                                                    AsyncImage(
                                                        contentScale = ContentScale.FillBounds,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .size(200.dp),
                                                        alignment = Alignment.Center,
                                                        model = "https://image.tmdb.org/t/p/w500" + movieSelected.poster,
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
                        Icon(modifier = Modifier.clickable {
                            val intent = Intent(context, ViewMoreActivity::class.java)
                            context.startActivity(intent)
                        }.weight(0.1f), imageVector = Icons.Default.ArrowForward, contentDescription = "Forward", tint = Color.White)
                    }
                    Spacer(
                        modifier = Modifier.padding(10.dp)
                    )

                    LazyRow{
                        itemsIndexed(movie.value.upcomingSeriesSelection) { _, seriesSelected ->
                            Card(
                                elevation = 4.dp,
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(170.dp),
                                backgroundColor = Color.Transparent,
                                content = {
                                    val context = LocalContext.current
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                isMovie = false
                                                val intent =
                                                    Intent(context, InfoActivity::class.java)
                                                intent.putExtra("id", seriesSelected.id)
                                                intent.putExtra("isMovie", isMovie)
                                                context.startActivity(intent)
                                            }
                                            .fillMaxWidth(), content = {
                                            Row(
                                                content = {
                                                    AsyncImage(
                                                        contentScale = ContentScale.FillBounds,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .size(200.dp),
                                                        alignment = Alignment.Center,
                                                        model = "https://image.tmdb.org/t/p/w500" + seriesSelected.poster,
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
                }
            }
            }
    }

}

