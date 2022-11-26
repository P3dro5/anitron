package com.example.anitron.data.datasource

import com.google.gson.annotations.SerializedName

class MovieCollection(
    @SerializedName("id")
    var id:String?,
    @SerializedName("name")
    var name:String?,
    @SerializedName("poster_path")
    var posterPath:String?,
    @SerializedName("backdrop_path")
    var backdropPath:String?,
)