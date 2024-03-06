package com.practicum.studyandroid.imdb.data

import com.practicum.studyandroid.imdb.data.dto.MovieSearchRequest
import com.practicum.studyandroid.imdb.data.dto.MovieSearchResponse
import com.practicum.studyandroid.imdb.domain.api.MoviesRepository
import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MovieSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> Resource.Error("Проверьте подключение к интернету")
            200 -> {
                val stored = localStorage.getSavedFavorites()

                return Resource.Success((response as MovieSearchResponse).results.map {
                    //Movie(it.id, it.resultType, it.image, it.title, it.description) зачем id и resultType?
                    Movie(it.id, it.title, it.description, it.image, stored.contains(it.id))
                })
            }
            else -> return Resource.Error("Ошибка сервера")
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }
}