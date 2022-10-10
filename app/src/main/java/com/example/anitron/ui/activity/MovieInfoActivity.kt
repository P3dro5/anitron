package com.example.anitron.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.anitron.R
import com.example.anitron.databinding.MovieInfoBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.MovieInfoViewModelFactory
import com.example.anitron.data.repository.MovieInfoRepository
import com.example.anitron.ui.viewmodel.MovieInfoViewModel

class MovieInfoActivity : AppCompatActivity(){
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var binding: MovieInfoBinding

    lateinit var viewModel: MovieInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        viewModel.movieInfo.observe(this){
            binding.imdbRating.text = it.imdbRating
        }
    }
}