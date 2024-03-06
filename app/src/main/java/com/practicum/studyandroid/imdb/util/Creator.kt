package com.practicum.studyandroid.imdb.util

import android.content.Context
import android.os.Bundle
import com.practicum.studyandroid.imdb.data.MoviesRepositoryImpl
import com.practicum.studyandroid.imdb.data.network.RetrofitNetworkClient
import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.domain.api.MoviesRepository
import com.practicum.studyandroid.imdb.domain.impl.MoviesInteractorImpl
import com.practicum.studyandroid.imdb.presentation.movies.MoviesSearchViewModel
import com.practicum.studyandroid.imdb.presentation.poster.PosterPresenter
import com.practicum.studyandroid.imdb.presentation.poster.PosterView

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun providePosterPresenter(posterView: PosterView, internalParams: Bundle?): PosterPresenter {
        return PosterPresenter(posterView, internalParams)
    }
}