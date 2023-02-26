package com.example.anitron.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitron.data.datasource.Movie
import com.example.anitron.data.datasource.SearchedPerson
import com.example.anitron.data.datasource.State
import com.example.anitron.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ViewMoreViewModel(private val repository: HomeRepository): ViewModel() {
    private val _entries =
        MutableStateFlow(ViewMoreEntryResult(state = State.Loading, movies = arrayListOf(), people = arrayListOf()))
    var entries = _entries

    private val movieTvShowList: MutableList<Movie> = arrayListOf()
    private val peopleList: MutableList<SearchedPerson> = arrayListOf()


    suspend fun getList(movieList: MutableList<Movie>, person: MutableList<SearchedPerson>) {
        _entries.emit(
            ViewMoreEntryResult(
                state = State.Success,
                movies = movieList,
                people = person
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
                getList(movieTvShowList, peopleList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreEntryResult(state = State.Failed, movies = arrayListOf(), people = arrayListOf()))
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
                getList(movieTvShowList, peopleList)

            } catch (e: Exception) {
                Log.e("ViewMoreTvShowResult", e.message ?: "", e)
                _entries.emit(ViewMoreEntryResult(state = State.Failed, movies = arrayListOf(), people = arrayListOf()))
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
                getList(movieTvShowList, peopleList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreEntryResult(state = State.Failed, movies = arrayListOf(), people = arrayListOf()))
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
                getList(movieTvShowList, peopleList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreEntryResult(state = State.Failed, movies = arrayListOf(), people = arrayListOf()))
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
                getList(movieTvShowList, peopleList)

            } catch (e: Exception) {
                Log.e("ViewMoreTvShowResult", e.message ?: "", e)
                _entries.emit(ViewMoreEntryResult(state = State.Failed, movies = arrayListOf(), people = arrayListOf()))
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
                getList(movieTvShowList, peopleList)

            } catch (e: Exception) {
                Log.e("ViewMoreMovieResult", e.message ?: "", e)
                _entries.emit(ViewMoreEntryResult(state = State.Failed, movies = arrayListOf(), people = arrayListOf()))
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
                getList(movieTvShowList, peopleList)

            } catch (e: Exception) {
                Log.e("ViewMoreTvShowResult", e.message ?: "", e)
                _entries.emit(ViewMoreEntryResult(state = State.Failed, movies = arrayListOf(), people = arrayListOf()))
            }
        }
    }

    fun getSearchedPeopleList(searchedQuery : String){
        viewModelScope.launch {
                try {
                for (i in 1..3) {
                    var searchedPeopleResponses = repository.getSearchPeople(i.toString(), searchedQuery)
                    for (person in searchedPeopleResponses.body()!!.results)
                        peopleList.add(person)
                }
                getList(movieTvShowList, peopleList)

            } catch (e: Exception) {
                    Log.e("ViewMoreTvShowResult", e.message ?: "", e)
                    _entries.emit(ViewMoreEntryResult(state = State.Failed, movies = arrayListOf(), people = arrayListOf()))
            }
        }
    }

}

data class ViewMoreEntryResult(val state: State, val movies: List<Movie>, val people: List<SearchedPerson>)
