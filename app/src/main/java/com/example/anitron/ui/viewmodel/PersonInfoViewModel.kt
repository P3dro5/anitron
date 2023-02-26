package com.example.anitron.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitron.data.datasource.State
import com.example.anitron.data.datasource.info.personInfo.MediaAppearances
import com.example.anitron.data.datasource.info.personInfo.PersonInfo
import com.example.anitron.data.repository.PersonInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PersonInfoViewModel(private val repository: PersonInfoRepository) : ViewModel() {
    private val _personInfo = MutableStateFlow(PersonInfoResult(state = State.Loading, personInfo = null, movieCredits = null, tvShowCredits = null))
    var personInfo = _personInfo

    fun getPersonInformation(id: String){
        viewModelScope.launch {
            try {
                val personDetailResponse = repository.getPersonDetail(id)
                val movieCreditsResponse = repository.getMoviePersonCredits(id)
                val tvShowCreditsResponse = repository.getTvPersonCredits(id)
                if (personDetailResponse.isSuccessful && movieCreditsResponse.isSuccessful && tvShowCreditsResponse.isSuccessful) {
                    _personInfo.emit(
                        PersonInfoResult(
                            state = State.Success,
                            personInfo = personDetailResponse.body(),
                            movieCredits = movieCreditsResponse.body(),
                            tvShowCredits = tvShowCreditsResponse.body()
                        )
                    )
                }
            }catch (e: Exception){
                _personInfo.emit(PersonInfoResult(state = State.Failed, personInfo = null, movieCredits = null, tvShowCredits = null))
            }
        }

    }

}

data class PersonInfoResult(val state: State, val personInfo: PersonInfo?, val movieCredits: MediaAppearances?, val tvShowCredits: MediaAppearances?)