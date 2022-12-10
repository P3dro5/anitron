package com.example.anitron.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitron.data.datasource.movieInfo.MovieInfo
import com.example.anitron.data.datasource.State
import com.example.anitron.data.datasource.tvshowInfo.TvShowInfo
import com.example.anitron.data.repository.MovieInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InfoViewModel(private val repository: MovieInfoRepository): ViewModel() {
    private val _movieInfo = MutableStateFlow(MovieInfoResult(state = State.Loading, movie = null))
    var movieInfo = _movieInfo
    private val _tvShowInfo = MutableStateFlow(TvShowInfoResult(state = State.Loading, tvShow = null))
    var tvShowInfo = _tvShowInfo
    private val errorMessage = MutableLiveData<String>()

    fun getMovieOnClick(id: String){
        viewModelScope.launch{
            try {
                val response = repository.getMovieSelected(id)
                if (response.isSuccessful && response.body() != null) {
                    _movieInfo.emit(
                        MovieInfoResult(
                            state = State.Success,
                            movie = response.body()
                        )
                    )
                } else {
                    errorMessage.value = response.message()
                }
            } catch(e: Exception){
                _movieInfo.emit(MovieInfoResult(state = State.Failed, movie = null))
            }
        }
    }

    fun getShowOnClick(id: String){
        viewModelScope.launch{
            try {
                val response = repository.getShowSelected(id)
                if (response.isSuccessful && response.body() != null) {
                    _tvShowInfo.emit(
                        TvShowInfoResult(
                            state = State.Success,
                            tvShow = response.body()
                        )
                    )
                } else {
                    errorMessage.value = response.message()
                }
            } catch(e: Exception){
                _movieInfo.emit(MovieInfoResult(state = State.Failed, movie = null))
            }
        }
    }
}

data class MovieInfoResult(val state: State, val movie: MovieInfo?)

data class TvShowInfoResult(val state: State, val tvShow: TvShowInfo?)