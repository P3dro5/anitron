package com.example.anitron.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import com.example.anitron.data.repository.MovieInfoRepository
import com.example.anitron.databinding.ActivityMainBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.InfoViewModelFactory
import com.example.anitron.ui.viewmodel.HomeViewModel
import com.example.anitron.ui.viewmodel.InfoViewModel

class InfoActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: InfoViewModel

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getStringExtra("id")

        viewModel =
            ViewModelProvider(this, InfoViewModelFactory(MovieInfoRepository(retrofitService)))[InfoViewModel::class.java]

        findViewById<ComposeView>(binding.homeViewDisplay.id)
            .setContent {
                if(movieId != null){
                    viewModel.getMovieOnClick(movieId)
                }
                Text(
                    text = movieId.toString()
                )
            }
    }

}