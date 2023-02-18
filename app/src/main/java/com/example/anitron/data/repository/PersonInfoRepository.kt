package com.example.anitron.data.repository

import com.example.anitron.domain.service.RetrofitService

class PersonInfoRepository(private val retrofitService: RetrofitService) {

    suspend fun getMoviePersonCredits(personId: String) = retrofitService.getMoviePersonCredits(personId)
    suspend fun getTvPersonCredits(personId: String) = retrofitService.getTvPersonCredits(personId)
    suspend fun getPersonDetail(personId: String) = retrofitService.getPersonDetail(personId)
}