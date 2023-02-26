package com.example.anitron.data.datasource.info.media.tvshowInfo

import com.google.gson.annotations.SerializedName

data class Network (
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("logo_path")
    var logoPath: String?,
    @SerializedName("origin_country")
    var originCountry: String?,
)