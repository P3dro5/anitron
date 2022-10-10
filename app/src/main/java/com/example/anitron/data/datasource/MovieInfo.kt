package com.example.anitron.data.datasource

import com.google.gson.annotations.SerializedName

class MovieInfo (
    @SerializedName("title")
    var title:String,
    @SerializedName("year")
    var year:String,
    @SerializedName("rated")
    var rated:String,
    @SerializedName("released")
    var released:String,
    @SerializedName("runtime")
    var runtime:String,
    @SerializedName("genre")
    var genre:String,
    @SerializedName("director")
    var director:String,
    @SerializedName("writer")
    var writer:String,
    @SerializedName("actors")
    var actors:String,
    @SerializedName("plot")
    var plot:String,
    @SerializedName("language")
    var language:String,
    @SerializedName("country")
    var country:String,
    @SerializedName("awards")
    var awards:String,
    @SerializedName("poster")
    var poster:String,
    @SerializedName("ratings")
    var ratings:List<Rating>,
    @SerializedName("metascore")
    var metascore:String,
    @SerializedName("imdbRating")
    var imdbRating: String,
    @SerializedName("imdbVotes")
    var imdbVotes: String,
    @SerializedName("imdbID")
    var imdbID: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("dvd")
    var dvd: String,
    @SerializedName("boxOffice")
    var boxOffice: String

    )