package com.practicum.studyandroid.imdb.presentation.movies

import com.practicum.studyandroid.imdb.domain.models.Movie

interface MoviesView {

    fun setPlaceholderVisibility(isVisible: Boolean)

    fun setMoviesListVisibility(isVisible: Boolean)

    fun setPlaceholderText(text: String)

    fun updateMovieList(newMoviesList: List<Movie>)

    fun showInstantMessage(text: String)

}