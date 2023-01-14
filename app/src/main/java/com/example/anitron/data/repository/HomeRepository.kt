package com.example.anitron.data.repository

import com.example.anitron.domain.service.RetrofitService

class HomeRepository(private val retrofitService: RetrofitService) {

    suspend fun getPopMovies(page : String) = retrofitService.getPopularMovies(page)
    suspend fun getPopSeries(page : String) = retrofitService.getPopularSeries(page)
    suspend fun getUpcMovies(page : String) = retrofitService.getUpcomingMovies(page)
    suspend fun getOnAir(page : String) = retrofitService.getOnTheAir(page)
    suspend fun getOnTheatres(page : String) = retrofitService.getOnTheatresMovies(page)
    suspend fun getSearchMovies(searchQuery: String) = retrofitService.getSearchMovies(searchQuery)
    suspend fun getSearchTvShow(searchQuery: String) = retrofitService.getSearchTvShows(searchQuery)
    suspend fun getSearchPeople(searchQuery: String) = retrofitService.getSearchPeople(searchQuery)

}