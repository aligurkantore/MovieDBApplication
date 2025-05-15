package com.example.moviedbapplication.domain.model.home.movienowplaying

data class MovieNowPlayingUI(
    val id: String,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val releaseDate: String
)

