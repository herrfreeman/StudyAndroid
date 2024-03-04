package com.practicum.studyandroid

import android.app.Application
import com.practicum.studyandroid.imdb.presentation.movies.MoviesSearchPresenter

class StudyApplication : Application() {
    var moviesSearchPresenter : MoviesSearchPresenter? = null
}