package com.practicum.studyandroid.imdb.data.network

import com.practicum.studyandroid.imdb.data.dto.MovieSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbApi {

    @GET("/en/API/SearchMovie/{apikey}/{query}")
    fun getMovies(
        @Path("apikey") apiKey: String,
        @Path("query") query: String
    ): Call<MovieSearchResponse>

}