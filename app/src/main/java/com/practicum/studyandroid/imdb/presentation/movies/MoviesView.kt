package com.practicum.studyandroid.imdb.presentation.movies

import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.ui.movies.models.MoviesState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MoviesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(state: MoviesState)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showInstantMessage(text: String)

}