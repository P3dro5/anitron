package com.example.anitron.data.datasource

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("genre_ids")
    var genreIds:List<String>,
    @SerializedName("id")
    var id:String,
    @SerializedName("original_language")
    var originalLanguage:String,
    @SerializedName("overview")
    var overview:String,
    @SerializedName("poster_path")
    var poster:String,
    @SerializedName("release_date")
    var releaseDate:String,
    @SerializedName("title")
    var title:String,
    @SerializedName("vote_average")
    var voteAverage:String,
)