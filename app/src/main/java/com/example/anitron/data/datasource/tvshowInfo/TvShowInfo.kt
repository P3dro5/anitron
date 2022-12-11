package com.example.anitron.data.datasource.tvshowInfo

import com.example.anitron.data.datasource.movieInfo.Company
import com.example.anitron.data.datasource.movieInfo.Genre
import com.example.anitron.data.datasource.movieInfo.Language
import com.google.gson.annotations.SerializedName

data class TvShowInfo (
    @SerializedName("created_by")
    var createdBy: List<Creator>,
    @SerializedName("genres")
    var genres: List<Genre>,
    @SerializedName("homepage")
    var homepage: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("in_production")
    var inProduction: Boolean,
    @SerializedName("last_air_date")
    var lastAirDate: String,
    @SerializedName("last_episode_to_air")
    var lastEpisodeToAir: Episode?,
    @SerializedName("name")
    var name: String,
    @SerializedName("networks")
    var networks: List<Network>,
    @SerializedName("number_of_episodes")
    var numOfEpisodes: String,
    @SerializedName("number_of_seasons")
    var numOfSeasons: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: String,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("production_companies")
    var productionCompanies:List<Company>,
    @SerializedName("seasons")
    var seasons:List<Season>,
    @SerializedName("spoken_languages")
    var spokenLanguages:List<Language>,
    @SerializedName("type")
    var type: String,
    @SerializedName("vote_average")
    var voteAverage: String,
    )