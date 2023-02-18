package com.example.anitron.data.datasource.info.media

import com.google.gson.annotations.SerializedName

data class MediaCast (
    @SerializedName("id")
    var name:String?,
    @SerializedName("id")
    var id:String?,
    @SerializedName("poster_path")
    var posterPath:String?,
)