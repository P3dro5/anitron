package com.example.anitron.data.datasource.tvshowInfo

import com.google.gson.annotations.SerializedName

data class Crew (
    @SerializedName("id") var id : String?,
    @SerializedName("department") var department : String?,
    @SerializedName("title") var title : String?,
    @SerializedName("overview") var overview : String?,
    @SerializedName("vote_count") var voteCount : String?,
    @SerializedName("poster_path") var posterPath : String?,
    @SerializedName("backdrop_path") var backdropPath : String?,
    @SerializedName("popularity") var popularity : String?,
    @SerializedName("genre_ids") var genreIds : List<String>,
    @SerializedName("cast") var cast : List<String>,
    @SerializedName("vote_average") var voteAverage : String?,
    @SerializedName("release_date") var releaseDate : String?,
    @SerializedName("credit_id") var creditId : String?,
)