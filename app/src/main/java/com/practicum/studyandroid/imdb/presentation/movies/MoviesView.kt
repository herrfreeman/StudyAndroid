package com.practicum.studyandroid.imdb.presentation.movies

import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.ui.movies.models.MoviesState

interface MoviesView {

    fun render(state: MoviesState)

    fun showInstantMessage(text: String)

}