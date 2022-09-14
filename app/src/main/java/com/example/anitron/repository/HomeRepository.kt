package com.example.anitron.repository

import com.example.anitron.service.RetrofitService

class HomeRepository(private val retrofitService: RetrofitService?) {

    fun getAllMovies() = retrofitService?.getAllMovies()
}