package com.example.anitron.data.datasource

import com.google.gson.annotations.SerializedName

data class PersonDetails (
    @SerializedName("results") var results: List<SearchedPerson>

)

data class SearchedPerson (
    @SerializedName("id") var id: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("profile_path") var personImgPath: String?,
)
