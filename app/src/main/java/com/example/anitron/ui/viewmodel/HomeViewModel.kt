package com.example.anitron.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitron.data.datasource.Movie
import com.example.anitron.data.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMovies() {

      viewModelScope.launch{
          val response = repository.getAllMovies()
          if(response.isSuccessful && response.body() != null){
              movieList.value = response.body()!!.mList
          }
          else{
              errorMessage.value = response.message()
          }
      }
    }
}