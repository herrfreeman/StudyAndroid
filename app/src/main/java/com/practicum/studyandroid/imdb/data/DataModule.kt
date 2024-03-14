package com.practicum.studyandroid.imdb.data

import com.practicum.studyandroid.imdb.data.network.RetrofitNetworkClient
import com.practicum.studyandroid.imdb.domain.api.MoviesRepository
import org.koin.dsl.module

val dataModule = module {
    single {
        LocalStorage(get())
    }
    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }
    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }
}