package com.example.anitron.data.repository

import com.example.anitron.domain.service.RetrofitService

class MovieInfoRepository(private val retrofitService: RetrofitService) {
    suspend fun getMovieSelected(movieId: String) = retrofitService.getMovieOnClick(movieId)
}