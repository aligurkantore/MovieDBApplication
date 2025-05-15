package com.example.moviedbapplication.domain.usecase.home.movienowplaying

import androidx.paging.PagingData
import androidx.paging.map
import com.example.moviedbapplication.domain.mapper.mapToMovieNowPlayingUI
import com.example.moviedbapplication.domain.model.home.movienowplaying.MovieNowPlayingUI
import com.example.moviedbapplication.domain.repository.MovieDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieNowPlayingUseCase @Inject constructor(private val repository: MovieDBRepository) {

    suspend operator fun invoke(): Flow<PagingData<MovieNowPlayingUI>> {
        return repository.getNowPlaying().map { pagingData ->
            pagingData.map { result ->
                result.mapToMovieNowPlayingUI()
            }
        }
    }
}