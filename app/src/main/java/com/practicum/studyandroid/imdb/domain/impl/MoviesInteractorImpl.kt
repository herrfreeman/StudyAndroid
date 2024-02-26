package com.practicum.studyandroid.imdb.domain.impl

import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.domain.api.MoviesRepository
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            consumer.consume(repository.searchMovies(expression))
        }
//        The same way to start in thread
//        val t = Thread {
//            consumer.consume(repository.searchMovies(expression))
//        }
//        t.start()
    }
}