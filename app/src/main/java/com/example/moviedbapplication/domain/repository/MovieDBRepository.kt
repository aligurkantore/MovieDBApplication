package com.example.moviedbapplication.domain.repository

import androidx.paging.PagingData
import com.example.moviedbapplication.common.util.Resource
import com.example.moviedbapplication.data.model.response.detail.MovieDetailResponse
import com.example.moviedbapplication.data.model.response.nowplaying.MovieNowPlayingItem
import com.example.moviedbapplication.data.model.response.toprated.MovietopRatedItem
import kotlinx.coroutines.flow.Flow

interface MovieDBRepository {

    suspend fun getNowPlaying(): Flow<PagingData<MovieNowPlayingItem>>

    suspend fun getTopRated(): Flow<PagingData<MovietopRatedItem>>

    suspend fun getPopular(): Flow<PagingData<MovieNowPlayingItem>>

    suspend fun getMovieDetails(movieId: String): Resource<MovieDetailResponse>
}