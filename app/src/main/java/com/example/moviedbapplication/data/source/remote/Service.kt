package com.example.moviedbapplication.data.source.remote

import com.example.moviedbapplication.common.util.Constants.Companion.BEARER_TOKEN
import com.example.moviedbapplication.data.model.response.detail.MovieDetailResponse
import com.example.moviedbapplication.data.model.response.nowplaying.MovieNowPlayingResponse
import com.example.moviedbapplication.data.model.response.toprated.MovieTopRatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface Service {

    @GET("movie/now_playing")
    suspend fun getPlayingList(
        @Header("Authorization") token: String = BEARER_TOKEN
    ): Response<MovieNowPlayingResponse>


    @GET("movie/top_rated")
    suspend fun getTopRatedList(
        @Header("Authorization") token: String = BEARER_TOKEN
    ): Response<MovieTopRatedResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: String,
        @Header("Authorization") token: String = BEARER_TOKEN
    ): Response<MovieDetailResponse>

    /*

    @GET("trending/all/day")
    suspend fun getTrendingList(@Header("Authorization") token: String): Response<MovieTrendingResponse>

    @GET("movie/popular")
    suspend fun getPopularList(@Header("Authorization") token: String): Response<MovieNowPlayingResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedList(@Header("Authorization") token: String): Response<MovieTrendingResponse>

    @GET("movie/upcoming")
    suspend fun getUpComingList(@Header("Authorization") token: String): Response<MovieNowPlayingResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvList(@Header("Authorization") token: String): Response<TvTopRatedResponse>

    @GET("tv/popular")
    suspend fun getPopularTvList(@Header("Authorization") token: String): Response<TvTopRatedResponse>

    @GET("trending/person/day")
    suspend fun getActorList(@Header("Authorization") token: String): Response<ActorResponse>

     */
    /*


     */
}