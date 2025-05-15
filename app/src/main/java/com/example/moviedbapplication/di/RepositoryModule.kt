package com.example.moviedbapplication.di

import com.example.moviedbapplication.data.repository.MovieDBRepositoryImpl
import com.example.moviedbapplication.data.source.remote.Service
import com.example.moviedbapplication.domain.repository.MovieDBRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        service: Service
    ): MovieDBRepository = MovieDBRepositoryImpl(
        service = service
    )
}