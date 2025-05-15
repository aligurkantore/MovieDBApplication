package com.example.moviedbapplication.domain.model.home.detail

data class MovieDetailUI(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    val voteAverage: Double,
    val releaseDate: String?,
    val genres: List<String>,
    val runtime: String,
    val productionCountries: List<String>,
    val spokenLanguages: List<String>,
    val tagline: String?,
    val status: String
)

