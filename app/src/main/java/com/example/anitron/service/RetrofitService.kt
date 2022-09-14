package com.example.anitron.service
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.anitron.MovieList
import com.example.anitron.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {


    @GET("?apikey="+BuildConfig.API_KEY+"&s=\"before\"&r=json&page=1")
    fun getAllMovies(): Call<MovieList>

    companion object {

        var retrofitService: RetrofitService? = null

        //Create the Retrofit service instance using the retrofit.
        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.omdbapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}