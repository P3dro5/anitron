package com.example.anitron.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitron.data.datasource.info.media.movieInfo.MovieInfo
import com.example.anitron.data.datasource.State
import com.example.anitron.data.datasource.info.media.Credits
import com.example.anitron.data.datasource.info.media.MediaCast
import com.example.anitron.data.datasource.info.media.tvshowInfo.TvShowInfo
import com.example.anitron.data.repository.MovieInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InfoViewModel(private val repository: MovieInfoRepository): ViewModel() {
    private val _movieInfo = MutableStateFlow(MovieInfoResult(state = State.Loading, movie = null, credits = null))
    var movieInfo = _movieInfo
    private val _tvShowInfo = MutableStateFlow(TvShowInfoResult(state = State.Loading, tvShow = null, credits = null))
    var tvShowInfo = _tvShowInfo
    private val errorMessage = MutableLiveData<String>()

    fun getMovieOnClick(id: String){
        viewModelScope.launch{
            try {
                val response = repository.getMovieSelected(id)
                val movieCredits = repository.getMovieCastCredits(id)
                if (response.isSuccessful && response.body() != null) {
                    _movieInfo.emit(
                        MovieInfoResult(
                            state = State.Success,
                            movie = response.body(),
                            credits = movieCredits.body()
                        )
                    )
                } else {
                    errorMessage.value = response.message()
                }
            } catch(e: Exception){
                _movieInfo.emit(MovieInfoResult(state = State.Failed, movie = null, credits = null))
            }
        }
    }

    fun getShowOnClick(id: String){
        viewModelScope.launch{
            try {
                val response = repository.getShowSelected(id)
                val tvShowCredits = repository.getTvShowCastCredits(id)
                if (response.isSuccessful && response.body() != null) {
                    _tvShowInfo.emit(
                        TvShowInfoResult(
                            state = State.Success,
                            tvShow = response.body(),
                            credits = tvShowCredits.body()
                        )
                    )
                } else {
                    errorMessage.value = response.message()
                }
            } catch(e: Exception){
                _movieInfo.emit(MovieInfoResult(state = State.Failed, movie = null, credits = null))
            }
        }
    }
}

data class MovieInfoResult(val state: State, val movie: MovieInfo?, val credits: Credits?)

data class TvShowInfoResult(val state: State, val tvShow: TvShowInfo?, val credits: Credits?)