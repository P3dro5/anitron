package com.example.anitron.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.anitron.R
import com.example.anitron.databinding.MovieInfoBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.MovieInfoViewModelFactory
import com.example.anitron.data.repository.MovieInfoRepository
import com.example.anitron.ui.theme.fonts
import com.example.anitron.ui.viewmodel.MovieInfoViewModel

class MovieInfoActivity : AppCompatActivity(){
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var binding: MovieInfoBinding

    lateinit var viewModel: MovieInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieInfoBinding.inflate(layoutInflater)
        /*setContentView(binding.root)

        val prevIntent = intent
        val imdbId = prevIntent.getStringExtra("imdbId")

        val id = findViewById<View>(R.id.imdbRating) as TextView

        //get viewModel instance using MyViewModelFactory
        viewModel =
            ViewModelProvider(this, MovieInfoViewModelFactory(MovieInfoRepository(retrofitService))).get(
                MovieInfoViewModel::class.java
            )
        if (imdbId != null) {
            viewModel.getMovieOnClick(imdbId)
        }
        */
        setContent {
            MovieInfoScreen()
        }
        /*
        viewModel.movieInfo.observe(this){
            binding.imdbRating.text = it.imdbRating
        }*/
    }

    @Composable
    private fun MovieInfoScreen() =
        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center){
            Text("Hello, GREETINGS",
                fontFamily = fonts, fontWeight = FontWeight.Bold, fontSize = 50.sp, color = Color.Green)
        }


}