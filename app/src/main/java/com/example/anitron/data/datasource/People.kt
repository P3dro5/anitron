package com.example.anitron.data.datasource

import com.google.gson.annotations.SerializedName

data class People (
    @SerializedName("cast") var cast : List<String>,
    @SerializedName("crew") var crew : List<String>,
    @SerializedName("id") var id : String?
    )