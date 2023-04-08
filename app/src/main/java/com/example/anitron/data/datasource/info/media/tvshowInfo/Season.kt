package com.example.anitron.data.datasource.info.media.tvshowInfo

import com.google.gson.annotations.SerializedName

data class Season(
    @SerializedName("air_date")
    var airDate:String?,
    @SerializedName("episode_count")
    var episodeCount:String?,
    @SerializedName("id")
    var id:String?,
    @SerializedName("name")
    var name:String?,
    @SerializedName("overview")
    var overview:String?,
    @SerializedName("poster_path")
    var poster_path:String?,
    @SerializedName("season_number")
    var seasonNum:String?
)