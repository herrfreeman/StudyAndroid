package com.practicum.studyandroid.imdb.util

import android.content.Context
import android.os.Bundle
import com.practicum.studyandroid.imdb.data.LocalStorage
import com.practicum.studyandroid.imdb.data.MoviesRepositoryImpl
import com.practicum.studyandroid.imdb.data.network.RetrofitNetworkClient
import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.domain.api.MoviesRepository
import com.practicum.studyandroid.imdb.domain.impl.MoviesInteractorImpl
import com.practicum.studyandroid.imdb.presentation.movies.MoviesSearchViewModel
import com.practicum.studyandroid.imdb.presentation.poster.PosterPresenter
import com.practicum.studyandroid.imdb.presentation.poster.PosterView
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.java.KoinJavaComponent.inject

object Creator {
//    private fun getMoviesRepository(context: Context): MoviesRepository {
//        return MoviesRepositoryImpl(
//            RetrofitNetworkClient(context),
//            LocalStorage(context.getSharedPreferences("local_storage", Context.MODE_PRIVATE)),
//        )
//    }

//    fun provideMoviesInteractor(context: Context): MoviesInteractor {
//        return MoviesInteractorImpl(getKoin().get())
//    }

    fun providePosterPresenter(posterView: PosterView, internalParams: Bundle?): PosterPresenter {
        return PosterPresenter(posterView, internalParams)
    }
}