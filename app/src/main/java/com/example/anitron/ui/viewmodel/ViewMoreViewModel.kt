package com.example.anitron.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitron.data.datasource.Movie
import com.example.anitron.data.datasource.State
import com.example.anitron.data.datasource.tvshowInfo.TvShowInfo
import com.example.anitron.data.repository.HomeRepository
import com.example.anitron.data.repository.ViewMoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ViewMoreViewModel(private val repository: HomeRepository): ViewModel() {
    private val _movies = MutableStateFlow(ViewMoreMovieResult(state = State.Loading, movies = arrayListOf()))
    var movies = _movies
    private val _tvShowInfo = MutableStateFlow(TvShowInfoResult(state = State.Loading, tvShow = null))
    var tvShowInfo = _tvShowInfo
    private val errorMessage = MutableLiveData<String>()
    private val movieList : MutableList<Movie> = arrayListOf()

    fun getPopularMovieList() {
        viewModelScope.launch{
            try{
                for(i in 1..5){
                    var popularMoviesResponses = repository.getPopMovies(i.toString())
                    for(movie in popularMoviesResponses.body()!!.mList)
                        movieList.add(movie)
                }

                if(movieList != null){
                    _movies.emit(
                        ViewMoreMovieResult(
                            state = State.Success,
                            movies = movieList,
                        )
                    )
                }
                else{
                    _movies.emit(ViewMoreMovieResult(state = State.Failed, movies = arrayListOf()))
                }
            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _movies.emit(ViewMoreMovieResult(state = State.Failed, movies = arrayListOf()))
            }
        }
    }
}

data class ViewMoreMovieResult(val state: State, val movies: List<Movie>)

data class ViewMoreTvShowResult(val state: State, val tvShow: TvShowInfo?)
