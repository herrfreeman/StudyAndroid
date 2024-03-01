package com.practicum.studyandroid.imdb.domain.api

import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
}