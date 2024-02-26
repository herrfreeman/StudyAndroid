package com.practicum.studyandroid.imdb.domain.api

import com.practicum.studyandroid.imdb.domain.models.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}