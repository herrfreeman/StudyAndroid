package com.practicum.studyandroid.imdb.domain.models

data class Movie(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val inFavorite: Boolean,
)