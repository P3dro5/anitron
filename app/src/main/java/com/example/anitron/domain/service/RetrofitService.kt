package com.example.anitron.domain.service
import com.example.anitron.data.datasource.MovieList
import com.example.anitron.BuildConfig
import com.example.anitron.data.datasource.movieInfo.MovieInfo
import com.example.anitron.data.datasource.tvshowInfo.TvShowInfo
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("3/movie/popular?language=en-US")
    suspend fun getPopularMovies(@Query("page") page : String): Response<MovieList>

    @GET("3/tv/popular?language=en-US")
    suspend fun getPopularSeries(@Query("page") page : String): Response<MovieList>

    @GET("3/movie/upcoming?language=en-US")
    suspend fun getUpcomingMovies(@Query("page") page : String): Response<MovieList>

    @GET("3/tv/on_the_air?language=en-US")
    suspend fun getOnTheAir(@Query("page") page : String): Response<MovieList>

    @GET("3/movie/now_playing?language=en-US")
    suspend fun getOnTheatresMovies(@Query("page") page : String): Response<MovieList>

    @GET("3/movie/{i}?language=en-US")
    suspend fun getMovieOnClick(@Path("i") imdbId : String): Response<MovieInfo>

    @GET("3/tv/{i}?language=en-US")
    suspend fun getTvShowOnClick(@Path("i") imdbId : String): Response<TvShowInfo>

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