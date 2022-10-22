package com.example.anitron.data.repository

import com.example.anitron.domain.service.RetrofitService

class HomeRepository(private val retrofitService: RetrofitService) {

    suspend fun getAllMovies(searchText: String) = retrofitService.getSearchingResultsMovies(searchText)
}