package com.example.anitron.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State as NormalState
import com.example.anitron.data.datasource.Movie
import com.example.anitron.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _movieList = MutableStateFlow(Result(state = State.Loading, movieSelection = listOf()))

    var movieList = _movieList
    var cachedList = movieList.value

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: NormalState<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: NormalState<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun searchMovies(value: List<Movie>){
        _movieList.value = Result(state = State.Loading, movieSelection = value)
    }
    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
    fun getSearchTextState():String{
        return _searchTextState.value
    }

    fun closeSearchBar(){
        movieList.update { cachedList }
    }


    fun getSearchMovies(searchText: String) {
      viewModelScope.launch{
          try {
          val response = repository.getAllMovies(searchText)
          if(response.isSuccessful && response.body() != null){
              var response = response.body()
              if(response != null){
                  _movieList.emit(
                      Result(
                          state = State.Success,
                          movieSelection = response.mList
                      )
                  )
              }

          }
          else{
              _movieList.emit(Result(state = State.Failed, movieSelection = listOf()))
          }
        } catch (e: Exception) {
            Log.e("ProductViewModel", e.message ?: "", e)
            _movieList.emit(Result(state = State.Failed, movieSelection = listOf()))
        }
      }
    }
}
data class Result(val state: State, val movieSelection: List<Movie>)

enum class State {
        Success,
        Failed,
        Loading
}
enum class SearchWidgetState {
        OPENED,
        CLOSED
}
