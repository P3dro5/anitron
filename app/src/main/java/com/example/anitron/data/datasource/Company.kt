package com.example.anitron.data.datasource

import com.google.gson.annotations.SerializedName

class Company (
    @SerializedName("id")
    var id:String,
    @SerializedName("poster_path")
    var posterPath:String,
)