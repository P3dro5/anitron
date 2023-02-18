package com.example.anitron.data.datasource.tvshowInfo

import com.google.gson.annotations.SerializedName

data class Cast (
    @SerializedName("character")
    var character : String?,
    @SerializedName("credit_id")
    var creditId:String?,
    @SerializedName("release_date")
    var releaseDate:String?,
    @SerializedName("vote_count")
    var voteCount:String?,
    @SerializedName("vote_average")
    var voteAverage:String?,
    @SerializedName("title")
    var title:String?,
    @SerializedName("genre_ids")
    var genreIds:List<String>,
    @SerializedName("popularity")
    var popularity:String?,
    @SerializedName("id")
    var id:String?,
    @SerializedName("backdrop_path")
    var backdropPath:String?,
    @SerializedName("overview")
    var overview:String?,
    @SerializedName("poster_path")
    var posterPath:String?,
)