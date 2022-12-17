package com.example.anitron.ui.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anitron.data.repository.HomeRepository
import com.example.anitron.data.repository.ViewMoreRepository
import com.example.anitron.ui.viewmodel.HomeViewModel
import com.example.anitron.ui.viewmodel.ViewMoreViewModel

class ViewMoreViewModelFactory constructor(private val repository: HomeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ViewMoreViewModel::class.java)) {
            ViewMoreViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}