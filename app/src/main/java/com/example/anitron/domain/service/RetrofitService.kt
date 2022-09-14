package com.example.anitron.domain.service
import com.example.anitron.data.datasource.MovieList
import com.example.anitron.BuildConfig
import com.example.anitron.data.datasource.MovieInfo
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    val idCalled: Int

    @GET("?s=before&r=json&page=1")
    suspend fun getAllMovies(): Response<MovieList>

    @GET("?r=json")
    suspend fun getMovieOnClick(@Query("i") imdbId : String): Response<MovieInfo>

    companion object {

      private fun createHttpClient() =
          OkHttpClient().newBuilder().addInterceptor { chain ->
              val newUrl =
                  chain.request().url.newBuilder()
                      .addQueryParameter("apikey", BuildConfig.API_KEY).build()
              chain.proceed(chain.request().newBuilder().url(newUrl).build())
          }.build()
        private var retrofitService: RetrofitService? = null

        //Create the Retrofit service instance using the retrofit.
        fun getInstance(): RetrofitService {
            if(retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .client(createHttpClient())
                    .baseUrl("https://omdbapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}