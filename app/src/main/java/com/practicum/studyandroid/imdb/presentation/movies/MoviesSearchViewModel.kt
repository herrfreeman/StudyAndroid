package com.practicum.studyandroid.imdb.presentation.movies

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.studyandroid.R

import com.practicum.studyandroid.imdb.util.Creator
import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.ui.movies.models.MoviesState
import com.practicum.studyandroid.imdb.util.SingleLiveEvent
import moxy.MvpPresenter

class MoviesSearchViewModel(application: Application) : AndroidViewModel(application) {

    private var latestSearchText: String? = null
    private val moviesInteractor = Creator.provideMoviesInteractor(getApplication<Application>())
    private val handler = Handler(Looper.getMainLooper())
    private val movies = mutableListOf<Movie>()

    private val stateLiveData = MutableLiveData<MoviesState>()

    private val mediatorStateLiveData = MediatorLiveData<MoviesState>().also { liveData ->
        liveData.addSource(stateLiveData) { movieState ->
            liveData.value = when (movieState) {
                is MoviesState.Content -> MoviesState.Content(movieState.movies.sortedByDescending { it.inFavorite })
                is MoviesState.Empty -> movieState
                is MoviesState.Error -> movieState
                is MoviesState.Loading -> movieState
            }
        }
    }

    //fun observeState(): LiveData<MoviesState> = stateLiveData
    fun observeState(): LiveData<MoviesState> = mediatorStateLiveData

    //private val toastState = MutableLiveData<String>()
    private val toastState = SingleLiveEvent<String>()
    fun observeToastState(): LiveData<String> = toastState

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MoviesSearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    private fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }

    fun searchNow(queryText: String) {
        searchDebounce(queryText, 0)
    }

    fun searchDebounce(changedText: String, debounceDelay: Long = SEARCH_DEBOUNCE_DELAY) {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText

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
            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(
                queryText,
                object : MoviesInteractor.MoviesConsumer {
                    override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {

                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }

                        when {
                            errorMessage != null -> {renderState(
                                    MoviesState.Error(
                                        getApplication<Application>().getString(
                                            R.string.something_went_wrong
                                        )
                                    )
                                )
                                showToast(errorMessage)
                            }

                            movies.isEmpty() -> {
                                renderState(
                                    MoviesState.Empty(
                                        getApplication<Application>().getString(
                                            R.string.nothing_found
                                        )
                                    )
                                )
                            }

                            else -> {
                                renderState(MoviesState.Content(movies))
                            }
                        }

                    }
                })
        }
    }

    fun showToast(message: String) {
        toastState.postValue(message)
    }

    fun toggleFavorite(movie: Movie) {
        if (movie.inFavorite) {
            moviesInteractor.removeMovieFromFavorites(movie)
        } else {
            moviesInteractor.addMovieToFavorites(movie)
        }
        updateMovieContent(movie.id, movie.copy(inFavorite = !movie.inFavorite))
    }

    private fun updateMovieContent(movieId: String, newMovie: Movie) {
        val currentState = stateLiveData.value

        if (currentState is MoviesState.Content) {
            val movieIndex = currentState.movies.indexOfFirst { it.id == movieId }
            if (movieIndex != -1) {
                stateLiveData.value = MoviesState.Content(
                    currentState.movies.toMutableList().also {
                        it[movieIndex] = newMovie
                    }
                )
            }
        }
    }



}

