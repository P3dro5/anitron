package com.example.anitron.ui.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anitron.data.repository.MovieInfoRepository
import com.example.anitron.ui.viewmodel.MovieInfoViewModel

class MovieInfoViewModelFactory constructor(private val repository: MovieInfoRepository) :
    ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MovieInfoViewModel::class.java)){
                MovieInfoViewModel(this.repository) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
}