package com.example.anitron.data.datasource.tvshowInfo

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("air_date")
    var airDate: String?,
    @SerializedName("episode_number")
    var episodeNumber: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("production_code")
    var productionCode: String?,
    @SerializedName("runtime")
    var runtime: String?,
    @SerializedName("season_number")
    var seasonNumber: String?,
    @SerializedName("show_id")
    var showId: String?,
    @SerializedName("still_path")
    var stillPath: String?,
    @SerializedName("vote_average")
    var voteAverage: String?,
    @SerializedName("vote_count")
    var voteCount: String?
)