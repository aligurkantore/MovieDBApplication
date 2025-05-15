package com.example.moviedbapplication.domain.mapper

import com.example.moviedbapplication.data.model.response.detail.MovieDetailResponse
import com.example.moviedbapplication.data.model.response.nowplaying.MovieNowPlayingItem
import com.example.moviedbapplication.data.model.response.toprated.MovietopRatedItem
import com.example.moviedbapplication.domain.model.home.detail.MovieDetailUI
import com.example.moviedbapplication.domain.model.home.movienowplaying.MovieNowPlayingUI
import com.example.moviedbapplication.domain.model.home.movietoprated.MovieTopRatedUI

fun MovieNowPlayingItem.mapToMovieNowPlayingUI(): MovieNowPlayingUI {
    return MovieNowPlayingUI(
        id = id.toString(),
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}

fun MovietopRatedItem.mapToMovieTopRatedUI(): MovieTopRatedUI {
    return MovieTopRatedUI(
        id = id.toString(),
        name = name ?: "",
        posterUrl = posterPath ,
        voteAverage = voteAverage,
        overview = overview
    )
}

fun MovieDetailResponse.toUI(): MovieDetailUI {
    return MovieDetailUI(
        id = id,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        genres = genres.map { it.name },
        runtime = formatRuntime(runtime),
        productionCountries = productionCountries.map { it.name },
        spokenLanguages = spokenLanguages.map { it.name },
        tagline = tagline,
        status = status
    )
}

private fun formatRuntime(runtime: Int): String {
    val hours = runtime / 60
    val minutes = runtime % 60
    return "${hours}h ${minutes}m"
}
