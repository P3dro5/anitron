package com.example.anitron.data.datasource
import com.google.gson.annotations.SerializedName

data class MovieList(@SerializedName("results") val mList : List<Movie>)
