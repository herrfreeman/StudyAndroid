package com.practicum.studyandroid.imdb.util

import android.app.Activity
import android.content.Context
import com.practicum.studyandroid.imdb.data.MoviesRepositoryImpl
import com.practicum.studyandroid.imdb.data.network.RetrofitNetworkClient
import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.domain.api.MoviesRepository
import com.practicum.studyandroid.imdb.domain.impl.MoviesInteractorImpl
import com.practicum.studyandroid.imdb.presentation.MoviesSearchController
import com.practicum.studyandroid.imdb.presentation.PosterController
import com.practicum.studyandroid.imdb.ui.movies.MovieAdapter

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchController(activity: Activity, adapter: MovieAdapter): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }
}