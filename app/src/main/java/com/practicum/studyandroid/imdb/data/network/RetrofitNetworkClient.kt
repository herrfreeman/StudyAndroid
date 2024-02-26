package com.practicum.studyandroid.imdb.data.network

import com.practicum.studyandroid.imdb.data.NetworkClient
import com.practicum.studyandroid.imdb.data.dto.MovieSearchRequest
import com.practicum.studyandroid.imdb.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {

    private val imdbBaseUrl = "https://tv-api.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(ImdbApi::class.java)

    override fun doRequest(dto: Any): Response {
        if (dto is MovieSearchRequest) {
            val resp = imdbService.getMovies("", dto.expression).execute()

            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}