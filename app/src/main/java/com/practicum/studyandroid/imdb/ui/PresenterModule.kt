package com.practicum.studyandroid.imdb.ui

import com.practicum.studyandroid.imdb.presentation.movies.MoviesSearchViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presenterModule = module{
    viewModel {
        MoviesSearchViewModel(get())
    }
}