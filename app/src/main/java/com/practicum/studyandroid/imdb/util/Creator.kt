package com.practicum.studyandroid.imdb.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.practicum.studyandroid.imdb.data.MoviesRepositoryImpl
import com.practicum.studyandroid.imdb.data.network.RetrofitNetworkClient
import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.domain.api.MoviesRepository
import com.practicum.studyandroid.imdb.domain.impl.MoviesInteractorImpl
import com.practicum.studyandroid.imdb.presentation.movies.MoviesSearchPresenter
import com.practicum.studyandroid.imdb.presentation.poster.PosterPresenter
import com.practicum.studyandroid.imdb.presentation.movies.MoviesView
import com.practicum.studyandroid.imdb.presentation.poster.PosterView

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(context: Context): MoviesSearchPresenter {
        return MoviesSearchPresenter(context)
    }

    fun providePosterPresenter(posterView: PosterView, internalParams: Bundle?): PosterPresenter {
        return PosterPresenter(posterView, internalParams)
    }
}