package com.practicum.studyandroid

import android.app.Application
import com.practicum.studyandroid.imdb.presentation.movies.MoviesSearchPresenter
import com.practicum.studyandroid.player.domain.TrackPlayer
import com.practicum.studyandroid.player.domain.TrackPlayerImpl
import com.practicum.studyandroid.player.domain.TracksInteractor
import com.practicum.studyandroid.player.domain.TracksInteractorImpl

class StudyApplication : Application() {
    var moviesSearchPresenter : MoviesSearchPresenter? = null

//    fun getRepository(): TracksRepositoryImpl {
//        return TracksRepositoryImpl(NetworkClientImpl())
//    }
    fun getRepository(): Any? {
        return null
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getRepository())
    }

    fun provideTrackPlayer() : TrackPlayer {
        return TrackPlayerImpl()
    }
}