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

    @GET("3/movie/popular?language=en-US")
    suspend fun getPopularMovies(): Response<MovieList>

    @GET("3/tv/popular?language=en-US")
    suspend fun getPopularSeries(): Response<MovieList>

    @GET("3/movie/upcoming?language=en-US")
    suspend fun getUpcomingMovies(): Response<MovieList>

    @GET("3/tv/on_the_air?language=en-US")
    suspend fun getOnTheAir(): Response<MovieList>

    @GET("3/movie/now_playing?language=en-US&page=1")
    suspend fun getOnTheatresMovies(): Response<MovieList>

    @GET("3/movie/?language=en-US")
    suspend fun getMovieOnClick(@Query("i") imdbId : String): Response<MovieInfo>

    companion object {

      private fun createHttpClient() =
          OkHttpClient().newBuilder().addInterceptor { chain ->
              val newUrl =
                  chain.request().url.newBuilder()
                      .addQueryParameter("api_key", BuildConfig.API_KEY).build()
              chain.proceed(chain.request().newBuilder().url(newUrl).build())
          }.build()
        private var retrofitService: RetrofitService? = null

        //Create the Retrofit service instance using the retrofit.
        fun getInstance(): RetrofitService {
            if(retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .client(createHttpClient())
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}