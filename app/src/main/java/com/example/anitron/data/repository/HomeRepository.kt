package com.example.anitron.data.repository

import com.example.anitron.domain.service.RetrofitService

class HomeRepository(private val retrofitService: RetrofitService) {

    suspend fun getPopMovies(page : String) = retrofitService.getPopularMovies(page)
    suspend fun getPopSeries(page : String) = retrofitService.getPopularSeries(page)
    suspend fun getUpcMovies(page : String) = retrofitService.getUpcomingMovies(page)
    suspend fun getOnAir(page : String) = retrofitService.getOnTheAir(page)
    suspend fun getOnTheatres(page : String) = retrofitService.getOnTheatresMovies(page)
    suspend fun getSearchMovies(page: String, searchQuery: String) = retrofitService.getSearchMovies(page, searchQuery)
    suspend fun getSearchTvShow(page: String, searchQuery: String) = retrofitService.getSearchTvShows(page, searchQuery)
    suspend fun getSearchPeople(page: String, searchQuery: String) = retrofitService.getSearchPeople(page, searchQuery)
    suspend fun getMoviePersonCredits(personId: String) = retrofitService.getMoviePersonCredits(personId)
    suspend fun getMovieTvCredits(personId: String) = retrofitService.getTvPersonCredits(personId)
    suspend fun getPersonDetail(personId: String) = retrofitService.getPersonDetail(personId)
}