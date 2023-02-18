package com.example.anitron.data.datasource.info.media

import com.google.gson.annotations.SerializedName

data class MediaCast (
    @SerializedName("name")
    var name:String?,
    @SerializedName("id")
    var id:String?,
    @SerializedName("profile_path")
    var profilePath:String?,
)