package com.example.anitron.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitron.data.datasource.*
import androidx.compose.runtime.State as NormalState
import com.example.anitron.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _movieGlobalList = MutableStateFlow(Result(state = State.Loading, movieSelection = listOf(), seriesSelection = listOf(), productionTeam = listOf(), upcomingMoviesSelection = listOf(), upcomingSeriesSelection = listOf(), onTheatres = listOf()))
    var movieList = _movieGlobalList

    private var cachedList = _movieGlobalList.value

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: NormalState<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: NormalState<String> = _searchTextState

    private val _bottomNavigationState:  MutableState<BottomNavigationState> =
        mutableStateOf(value = BottomNavigationState.Home)
    var bottomNavigationState: NormalState<BottomNavigationState> = _bottomNavigationState

    private val _profileState: MutableState<ProfileState> =
        mutableStateOf(value = ProfileState.Default)
    var profileState: NormalState<ProfileState> = _profileState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun closeSearchBar(){
        movieList.update { cachedList }
    }

    fun getHomeScreenMoviesAndSeries() {
        viewModelScope.launch{
            try{
                val popularMoviesResponses = repository.getPopMovies("1")
                val popularSeriesResponses = repository.getPopSeries("1")
                val upcomingMoviesResponses = repository.getUpcMovies("1")
                val upcomingSeriesResponses = repository.getOnAir("1")
                val onTheatres = repository.getOnTheatres("1")


                if(popularMoviesResponses.isSuccessful && popularMoviesResponses.body() != null && popularSeriesResponses.isSuccessful && popularSeriesResponses.body() != null){
                        _movieGlobalList.emit(
                            Result(
                                state = State.Success,
                                movieSelection = popularMoviesResponses.body()!!.mList,
                                seriesSelection = popularSeriesResponses.body()!!.mList,
                                productionTeam = listOf(),
                                upcomingMoviesSelection = upcomingMoviesResponses.body()!!.mList,
                                upcomingSeriesSelection = upcomingSeriesResponses.body()!!.mList,
                                onTheatres = onTheatres.body()!!.mList
                            )
                        )
                }
                else{
                    _movieGlobalList.emit(Result(state = State.Failed, movieSelection = listOf(), seriesSelection = listOf(), productionTeam = listOf(), upcomingMoviesSelection = listOf(), upcomingSeriesSelection = listOf(), onTheatres = listOf() ))
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", e.message ?: "", e)
                _movieGlobalList.emit(Result(state = State.Failed, movieSelection = listOf(), seriesSelection = listOf(), productionTeam = listOf(), upcomingMoviesSelection = listOf(), upcomingSeriesSelection = listOf(), onTheatres = listOf()))
            }
        }
    }

    fun getSearchMovies(searchText: String) {
      viewModelScope.launch{
          try {
              val movieResponse = repository.getSearchMovies("1", searchText)
              val seriesResponse = repository.getSearchTvShow("1", searchText)
              val peopleResponse = repository.getSearchPeople("1", searchText)

              if(movieResponse.isSuccessful && movieResponse.body() != null && seriesResponse.isSuccessful && seriesResponse.body() != null && peopleResponse.isSuccessful && peopleResponse.body() != null){
                  var movieResponseBody = movieResponse.body()
                  var seriesResponseBody = seriesResponse.body()
                  var peopleResponseBody = peopleResponse.body()

                  _movieGlobalList.getAndUpdate {
                        Result(
                            state = State.Searched,
                            movieSelection = movieResponseBody!!.mList,
                            seriesSelection = seriesResponseBody!!.mList,
                            productionTeam = peopleResponseBody!!.results,
                            upcomingMoviesSelection = listOf(),
                            upcomingSeriesSelection = listOf(),
                            onTheatres = listOf(),
                        )
                  }
          }
          else{
              _movieGlobalList.getAndUpdate {
                  Result(
                      state = State.Failed,
                      movieSelection = listOf(),
                      seriesSelection = listOf(),
                      productionTeam = listOf(),
                      upcomingMoviesSelection = listOf(),
                      upcomingSeriesSelection = listOf(),
                      onTheatres = listOf(),
                  )
              }
          }
        } catch (e: Exception) {
            Log.e("HomeViewModel", e.message ?: "", e)
              _movieGlobalList.getAndUpdate {
                  Result(
                      state = State.Failed,
                      movieSelection = listOf(),
                      seriesSelection = listOf(),
                      productionTeam = listOf(),
                      upcomingMoviesSelection = listOf(),
                      upcomingSeriesSelection = listOf(),
                      onTheatres = listOf(),
                  )
              }
        }
      }
    }

    fun bottomNavigationScreenChange(screenState: BottomNavigationState){
        _bottomNavigationState.value = screenState
    }

    fun profileStateChange(profileState: ProfileState){
        _profileState.value = profileState
    }
}

data class Result(val state: State, val movieSelection: List<Movie>, val seriesSelection: List<Movie>, val productionTeam: List<SearchedPerson>, val upcomingMoviesSelection: List<Movie>, val upcomingSeriesSelection: List<Movie>, val onTheatres: List<Movie>)

data class Screen(val screenState: State)

data class EditProfile(val profileState: ProfileState)