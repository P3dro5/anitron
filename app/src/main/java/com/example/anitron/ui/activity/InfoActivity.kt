package com.example.anitron.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import com.example.anitron.data.repository.MovieInfoRepository
import com.example.anitron.databinding.MovieInfoBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.InfoViewModelFactory
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

        viewModel =
            ViewModelProvider(this, InfoViewModelFactory(MovieInfoRepository(retrofitService)))[InfoViewModel::class.java]

        findViewById<ComposeView>(binding.infoViewDisplay.id)
            .setContent {
                if(isMovie) {
                    if (movieTvShowId != null) {
                        viewModel.getMovieOnClick(movieTvShowId)
                    }
                }
                else{
                    if (movieTvShowId != null) {
                        //viewModel.getTvShowOnClick(movieTvShowId)
                    }
                }
                Text(
                    text = movieTvShowId.toString()
                )
            }
    }

}