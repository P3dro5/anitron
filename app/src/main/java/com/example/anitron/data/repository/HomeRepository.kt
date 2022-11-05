package com.example.anitron.data.repository

import com.example.anitron.domain.service.RetrofitService

class HomeRepository(private val retrofitService: RetrofitService) {

    suspend fun getPopMovies() = retrofitService.getPopularMovies()
    suspend fun getPopSeries() = retrofitService.getPopularSeries()
    suspend fun getUpcMovies() = retrofitService.getUpcomingMovies()
    suspend fun getOnAir() = retrofitService.getOnTheAir()
    suspend fun getOnTheatres() = retrofitService.getOnTheatresMovies()

}