package com.example.anitron.data.datasource

import com.google.gson.annotations.SerializedName

data class People (
    @SerializedName("cast") var cast : List<Cast>,
    @SerializedName("crew") var crew : List<Crew>,
    @SerializedName("id") var id : String?
    )