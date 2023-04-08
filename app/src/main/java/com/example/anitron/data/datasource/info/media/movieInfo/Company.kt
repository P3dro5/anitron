package com.example.anitron.data.datasource.info.media.movieInfo

import com.google.gson.annotations.SerializedName

class Company (
    @SerializedName("id")
    var id:String?,
    @SerializedName("logo_path")
    var posterPath:String?,
    @SerializedName("name")
    var name:String?
)