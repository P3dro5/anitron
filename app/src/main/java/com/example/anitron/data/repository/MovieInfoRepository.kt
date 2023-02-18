package com.example.anitron.data.repository

import com.example.anitron.domain.service.RetrofitService

class MovieInfoRepository(private val retrofitService: RetrofitService) {
    suspend fun getMovieSelected(movieId: String) = retrofitService.getMovieOnClick(movieId)
    suspend fun getShowSelected(tvShowId: String) = retrofitService.getTvShowOnClick(tvShowId)
    suspend fun getTvShowCastCredits(tvShowId: String) = retrofitService.getTvShowCastCredits(tvShowId)
    suspend fun getMovieCastCredits(movieId: String) = retrofitService.getMovieCastCredits(movieId)
}