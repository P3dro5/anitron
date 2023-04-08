package com.example.anitron.data.datasource.info.personInfo

import com.google.gson.annotations.SerializedName

data class PersonInfo (
    @SerializedName("biography") var biography : String,
    @SerializedName("birthday") var birthday : String,
    @SerializedName("deathday") var deathDay : String,
    @SerializedName("gender") var gender : String,
    @SerializedName("id") var id : String?,
    @SerializedName("known_for_department") var role: String,
    @SerializedName("name") var name: String,
    @SerializedName("place_of_birth") var birthLocation: String,
    @SerializedName("profile_path") var profilePersonImage: String
)