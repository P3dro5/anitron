package com.example.anitron.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitron.data.datasource.MovieInfo
import kotlinx.coroutines.launch
import com.example.anitron.data.repository.MovieInfoRepository

class MovieInfoViewModel(private val repository: MovieInfoRepository): ViewModel() {
    val movieInfo = MutableLiveData<MovieInfo>()
    val errorMessage = MutableLiveData<String>()

    fun getMovieOnClick(imdbId: String){
        viewModelScope.launch{
            val response = repository.getMovieOnClick(imdbId)
            if(response.isSuccessful && response.body() != null){
                movieInfo.value = response.body()
            }
            else{
                errorMessage.value = response.message()
            }
        }
    }
}