package com.example.moviedbapplication.domain.usecase.home.movietoprated

import androidx.paging.PagingData
import androidx.paging.map
import com.example.moviedbapplication.domain.mapper.mapToMovieTopRatedUI
import com.example.moviedbapplication.domain.model.home.movietoprated.MovieTopRatedUI
import com.example.moviedbapplication.domain.repository.MovieDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieTopRatedUseCase @Inject constructor(private val repository: MovieDBRepository){

    suspend operator fun invoke(): Flow<PagingData<MovieTopRatedUI>> {
        return repository.getTopRated().map { pagingData ->
            pagingData.map { result ->
                result.mapToMovieTopRatedUI()
            }
        }
    }
}