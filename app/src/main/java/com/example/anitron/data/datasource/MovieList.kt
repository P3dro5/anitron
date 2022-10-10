package com.example.anitron.data.datasource
import com.example.anitron.data.datasource.Movie
import com.google.gson.annotations.SerializedName

data class MovieList(@SerializedName("Search") val mList : List<Movie>)
