package com.example.moviedbapplication.domain.usecase.detail

import com.example.moviedbapplication.common.util.onSuccess
import com.example.moviedbapplication.domain.mapper.toUI
import com.example.moviedbapplication.domain.repository.MovieDBRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(private val repository: MovieDBRepository) {

     operator fun invoke(movieId: String) = flow {
        emit(
            repository.getMovieDetails(movieId).onSuccess {
                it.toUI()
            }
        )
    }
}