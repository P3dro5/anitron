package com.example.anitron.data.datasource.movieInfo

import com.example.anitron.data.datasource.MovieCollection
import com.google.gson.annotations.SerializedName

class MovieInfo (
    @SerializedName("belongs_to_collection")
    var belongsToCollection: MovieCollection?,
    @SerializedName("budget")
    var budget:String,
    @SerializedName("genres")
    var genres:List<Genre>,
    @SerializedName("homepage")
    var homepage:String,
    @SerializedName("id")
    var id:String,
    @SerializedName("overview")
    var overview:String,
    @SerializedName("poster_path")
    var posterPath:String,
    @SerializedName("production_companies")
    var productionCompanies:List<Company>,
    @SerializedName("release_date")
    var releaseDate:String,
    @SerializedName("revenue")
    var revenue:String,
    @SerializedName("runtime")
    var runtime:String,
    @SerializedName("spoken_languages")
    var spokenLanguages:List<Language>,
    @SerializedName("status")
    var status:String,
    @SerializedName("tagline")
    var tagline:String,
    @SerializedName("title")
    var title:String,
    @SerializedName("vote_average")
    var voteAverage: String,
    )