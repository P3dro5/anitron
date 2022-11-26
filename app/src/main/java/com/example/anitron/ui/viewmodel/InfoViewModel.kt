package com.example.anitron.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitron.data.datasource.MovieInfo
import com.example.anitron.data.datasource.State
import com.example.anitron.data.repository.MovieInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InfoViewModel(private val repository: MovieInfoRepository): ViewModel() {
    private val _movieTvShowInfo = MutableStateFlow(InfoResult(state = State.Loading, movieTvShow = null))
    var movieTvShowInfo = _movieTvShowInfo
    private val errorMessage = MutableLiveData<String>()

    fun getMovieOnClick(id: String){
        viewModelScope.launch{
            try {
                val response = repository.getMovieSelected(id)
                if (response.isSuccessful && response.body() != null) {
                    _movieTvShowInfo.emit(
                        InfoResult(
                            state = State.Success,
                            movieTvShow = response.body()
                        )
                    )
                } else {
                    errorMessage.value = response.message()
                }
            } catch(e: Exception){
                _movieTvShowInfo.emit(InfoResult(state = State.Failed, movieTvShow = null))
            }
        }
    }

    fun getShowOnClick(id: String){
        viewModelScope.launch{
            try {
                val response = repository.getShowOnClick(id)
                if (response.isSuccessful && response.body() != null) {
                    _movieTvShowInfo.emit(
                        InfoResult(
                            state = State.Success,
                            movieTvShow = response.body()
                        )
                    )
                } else {
                    errorMessage.value = response.message()
                }
            } catch(e: Exception){
                _movieTvShowInfo.emit(InfoResult(state = State.Failed, movieTvShow = null))
            }
        }
    }
}

data class InfoResult(val state: State, val movieTvShow: MovieInfo?)