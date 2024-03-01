package com.practicum.studyandroid.imdb.data

import com.practicum.studyandroid.imdb.data.dto.MovieSearchRequest
import com.practicum.studyandroid.imdb.data.dto.MovieSearchResponse
import com.practicum.studyandroid.imdb.domain.api.MoviesRepository
import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.util.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MovieSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> Resource.Error("Проверьте подключение к интернету")
            200 -> {
                return Resource.Success((response as MovieSearchResponse).results.map {
                    //Movie(it.id, it.resultType, it.image, it.title, it.description) зачем id и resultType?
                    Movie(it.title, it.description, it.image)
                })
            }
            else -> return Resource.Error("Ошибка сервера")
        }
    }
}