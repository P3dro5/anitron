package com.example.anitron.data.datasource.movieInfo

import com.google.gson.annotations.SerializedName

class Genre(
    @SerializedName("id")
    var id:String?,
    @SerializedName("name")
    var name:String?,
)