package com.example.anitron.data.datasource.tvshowInfo

import com.google.gson.annotations.SerializedName

data class MediaAppearances (
    @SerializedName("cast") var cast : List<Cast>,
    @SerializedName("crew") var crew : List<Crew>,
    @SerializedName("id") var id : String,
)