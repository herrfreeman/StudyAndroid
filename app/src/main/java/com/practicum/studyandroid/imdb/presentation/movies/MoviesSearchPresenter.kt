package com.practicum.studyandroid.imdb.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.practicum.studyandroid.R

import com.practicum.studyandroid.imdb.util.Creator
import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.ui.movies.MovieAdapter
import com.practicum.studyandroid.imdb.ui.movies.models.MoviesState

class MoviesSearchPresenter(private val view: MoviesView,
                            private val context: Context) {

    private val moviesInteractor = Creator.provideMoviesInteractor(context)
    private val handler = Handler(Looper.getMainLooper())
    private val movies = mutableListOf<Movie>()

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
    }

    fun searchNow(queryText: String) {
        searchDebounce(queryText, 0)
    }

    fun searchDebounce(changedText: String, debounceDelay: Long = SEARCH_DEBOUNCE_DELAY) {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { searchRequest(changedText) }

        val postTime = SystemClock.uptimeMillis() + debounceDelay
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }

    fun searchRequest(queryText: String) {
        if (queryText.isNotEmpty()) {
            view.render(MoviesState.Loading)

            moviesInteractor.searchMovies(
                queryText,
                object : MoviesInteractor.MoviesConsumer {
                    override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                        handler.post {
                            if (foundMovies != null) {
                                movies.clear()
                                movies.addAll(foundMovies)
                            }

                            when {
                                errorMessage != null -> {
                                    view.render(MoviesState.Error(context.getString(R.string.something_went_wrong)))
                                    view.showInstantMessage(errorMessage)
                                }

                                movies.isEmpty() -> {
                                    view.render(MoviesState.Empty(context.getString(R.string.nothing_found)))
                                }

                                else -> {
                                    view.render(MoviesState.Content(movies))
                                }
                            }
                        }
                    }
                })
        }
    }

}