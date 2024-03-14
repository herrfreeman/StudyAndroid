package com.practicum.studyandroid.imdb.domain

import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }
}