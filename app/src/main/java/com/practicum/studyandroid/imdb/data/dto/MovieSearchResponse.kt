package com.practicum.studyandroid.imdb.data.dto

import com.practicum.studyandroid.imdb.domain.models.Movie

class MovieSearchResponse(val searchType: String,
                          val expression: String,
                          val results: List<MovieDto>) : Response()