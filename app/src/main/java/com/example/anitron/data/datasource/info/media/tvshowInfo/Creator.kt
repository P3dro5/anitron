package com.example.anitron.data.datasource.info.media.tvshowInfo

import com.google.gson.annotations.SerializedName

data class Creator (
    @SerializedName("id")
    var id: String?,
    @SerializedName("credit_id")
    var creditId: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("gender")
    var gender: String?,
    @SerializedName("profile_path")
    var profilePath: String?,
        )