package com.practicum.studyandroid.imdb.data

import com.practicum.studyandroid.imdb.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}