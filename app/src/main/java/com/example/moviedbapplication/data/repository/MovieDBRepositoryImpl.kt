package com.example.moviedbapplication.data.repository

import androidx.paging.PagingData
import com.example.moviedbapplication.common.base.BaseRepository
import com.example.moviedbapplication.common.util.Resource
import com.example.moviedbapplication.common.util.onSuccess
import com.example.moviedbapplication.data.model.response.detail.MovieDetailResponse
import com.example.moviedbapplication.data.model.response.nowplaying.MovieNowPlayingItem
import com.example.moviedbapplication.data.model.response.toprated.MovietopRatedItem
import com.example.moviedbapplication.data.source.remote.Service
import com.example.moviedbapplication.domain.repository.MovieDBRepository
import kotlinx.coroutines.flow.Flow

class MovieDBRepositoryImpl (
    private val service: Service
) : BaseRepository(), MovieDBRepository {

    override suspend fun getNowPlaying(): Flow<PagingData<MovieNowPlayingItem>> {
        return safeApiCallPaging { page, pageSize ->
            safeApiCall { service.getPlayingList() }.onSuccess { response ->
                response.movieNowPlayingItems
            }
        }
    }

    override suspend fun getTopRated(): Flow<PagingData<MovietopRatedItem>> {
        return safeApiCallPaging { page, pageSize ->
            safeApiCall { service.getTopRatedList() }.onSuccess { response ->
                response.movietopRatedItems
            }
        }
    }

    override suspend fun getPopular(): Flow<PagingData<MovieNowPlayingItem>> {
        return safeApiCallPaging { page, pageSize ->
            safeApiCall { service.getPopularList() }.onSuccess { response ->
                response.movieNowPlayingItems
            }
        }
    }

    override suspend fun getMovieDetails(movieId: String) = safeApiCall {
        service.getMovieDetail(movieId)
    }

}