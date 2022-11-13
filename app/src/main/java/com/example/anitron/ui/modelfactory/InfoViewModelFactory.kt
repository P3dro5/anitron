package com.example.anitron.ui.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anitron.data.repository.MovieInfoRepository
import com.example.anitron.ui.viewmodel.InfoViewModel

class InfoViewModelFactory constructor(private val repository: MovieInfoRepository) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(InfoViewModel::class.java)){
            InfoViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}