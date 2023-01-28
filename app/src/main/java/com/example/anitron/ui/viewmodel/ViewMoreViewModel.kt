package com.example.anitron.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitron.data.datasource.Movie
import com.example.anitron.data.datasource.State
import com.example.anitron.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ViewMoreViewModel(private val repository: HomeRepository): ViewModel() {
    private val _entries =
        MutableStateFlow(ViewMoreMovieResult(state = State.Loading, movies = arrayListOf()))
    var entries = _entries

    private val movieTvShowList: MutableList<Movie> = arrayListOf()

    suspend fun getList(movieList: MutableList<Movie>) {
        _entries.emit(
            ViewMoreMovieResult(
                state = State.Success,
                movies = movieList,
            )
        )
    }

    fun getPopularMovieList() {
        viewModelScope.launch {
            try {
                for (i in 1..5) {
                    var popularMoviesResponses = repository.getPopMovies(i.toString())
                    for (movie in popularMoviesResponses.body()!!.mList)
                        movieTvShowList.add(movie)
                }
                getList(movieTvShowList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreMovieResult(state = State.Failed, movies = arrayListOf()))
            }
        }
    }

    fun getPopularTvShowList() {
        viewModelScope.launch {
            try {
                for (i in 1..5) {
                    var popularTvShowResponses = repository.getPopSeries(i.toString())
                    for (movie in popularTvShowResponses.body()!!.mList)
                        movieTvShowList.add(movie)
                }
                getList(movieTvShowList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreMovieResult(state = State.Failed, movies = arrayListOf()))
            }
        }
    }

    fun getOnTheatresList() {
        viewModelScope.launch {
            try {
                for (i in 1..5) {
                    var onTheatresResponses = repository.getOnTheatres(i.toString())
                    for (movie in onTheatresResponses.body()!!.mList)
                        movieTvShowList.add(movie)
                }
                getList(movieTvShowList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreMovieResult(state = State.Failed, movies = arrayListOf()))
            }
        }
    }

    fun getUpcomingMoviesList() {
        viewModelScope.launch {
            try {
                for (i in 1..5) {
                    var upcomingMovieResponses = repository.getUpcMovies(i.toString())
                    for (movie in upcomingMovieResponses.body()!!.mList)
                        movieTvShowList.add(movie)
                }
                getList(movieTvShowList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreMovieResult(state = State.Failed, movies = arrayListOf()))
            }
        }
    }

    fun getShowsCurrentlyAiringList() {
        viewModelScope.launch {
            try {
                for (i in 1..5) {
                    var showsAiringResponses = repository.getOnAir(i.toString())
                    for (movie in showsAiringResponses.body()!!.mList)
                        movieTvShowList.add(movie)
                }
                getList(movieTvShowList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreMovieResult(state = State.Failed, movies = arrayListOf()))
            }
        }
    }

    fun getSearchedMoviesList(searchedQuery : String){
        viewModelScope.launch {
            try {
                for (i in 1..5) {
                    var searchedMovieResponses = repository.getSearchMovies(i.toString(), searchedQuery)
                    for (movie in searchedMovieResponses.body()!!.mList)
                        movieTvShowList.add(movie)
                }
                getList(movieTvShowList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreMovieResult(state = State.Failed, movies = arrayListOf()))
            }
        }
    }

    fun getSearchedTvShowList(searchedQuery : String){
        viewModelScope.launch {
            try {
                for (i in 1..5) {
                    var searchedTvShowResponses = repository.getSearchTvShow(i.toString(), searchedQuery)
                    for (tvShow in searchedTvShowResponses.body()!!.mList)
                        movieTvShowList.add(tvShow)
                }
                getList(movieTvShowList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreMovieResult(state = State.Failed, movies = arrayListOf()))
            }
        }
    }

}

data class ViewMoreMovieResult(val state: State, val movies: List<Movie>)
