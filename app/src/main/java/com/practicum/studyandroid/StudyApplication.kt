package com.practicum.studyandroid

import android.app.Application
import com.practicum.studyandroid.imdb.data.dataModule
import com.practicum.studyandroid.imdb.domain.domainModule
import com.practicum.studyandroid.imdb.presentation.movies.MoviesSearchViewModel
import com.practicum.studyandroid.imdb.ui.presenterModule
import com.practicum.studyandroid.player.domain.TrackPlayer
import com.practicum.studyandroid.player.domain.TrackPlayerImpl
import com.practicum.studyandroid.player.domain.TracksInteractor
import com.practicum.studyandroid.player.domain.TracksInteractorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/* DAGGER DOESNT WORK
class DaggerTestClass @Inject constructor() {
    val answer = 42
}

@Component
interface ApplicationComponent {
    fun getTest(): DaggerTestClass
}*/

class StudyApplication : Application() {

    var moviesSearchViewModel : MoviesSearchViewModel? = null

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@StudyApplication)
            modules(dataModule, presenterModule, domainModule)
        }
    }

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

//@Component
//interface ApplicationGraph {
//    fun repository(): TracksInteractorImpl
//}