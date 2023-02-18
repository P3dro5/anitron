package com.example.anitron.data.datasource.info.media

import com.google.gson.annotations.SerializedName

class Credits(
    @SerializedName("id")
    var id:String?,
    @SerializedName("cast")
    var cast:List<MediaCast?>
)