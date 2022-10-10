package com.example.anitron.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anitron.ui.modelfactory.HomeViewModelFactory
import com.example.anitron.ui.adapter.MovieRecyclerAdapter
import com.example.anitron.databinding.ActivityMainBinding
import com.example.anitron.data.repository.HomeRepository
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.viewmodel.HomeViewModel


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
            layoutManager = GridLayoutManager(applicationContext,3)
        }
        //set recyclerview adapter
        binding.recycler.adapter = adapter

        viewModel.movieList.observe(this, Observer {
            Log.d(TAG, "movieList: $it")
            adapter.setMovieList(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
        })

        viewModel.getAllMovies()


    }
}


