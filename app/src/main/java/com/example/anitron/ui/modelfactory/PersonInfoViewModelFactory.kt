package com.example.anitron.ui.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anitron.data.repository.PersonInfoRepository
import com.example.anitron.ui.viewmodel.PersonInfoViewModel

class PersonInfoViewModelFactory constructor(private val repository: PersonInfoRepository) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PersonInfoViewModel::class.java)){
            PersonInfoViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}