package com.practicum.studyandroid.imdb.presentation

import android.app.Activity

import android.os.Handler
import android.os.Looper
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

class MoviesSearchController(private val activity: Activity,
                             private val adapter: MovieAdapter) {

    private val moviesInteractor = Creator.provideMoviesInteractor(activity)
    private val handler = Handler(Looper.getMainLooper())

//    private val movieList: MutableList<Movie> = emptyList<Movie>().toMutableList()
    private val movieList = adapter.movies

    private lateinit var queryMovieInput: EditText
    private lateinit var moviePlaceholderMessage: TextView
    private lateinit var movieListView: RecyclerView
    private lateinit var searchMovieButton: Button

    fun onCreate() {
        queryMovieInput = activity.findViewById(R.id.queryMovieInput)
        moviePlaceholderMessage = activity.findViewById(R.id.moviePlaceholderMessage)
        movieListView = activity.findViewById(R.id.movieList)
        searchMovieButton = activity.findViewById(R.id.searchMovieButton)

        searchMovieButton.setOnClickListener{ searchRequest() }
    }

    private fun searchRequest() {
        if (queryMovieInput.text.isNotEmpty()) {

            moviePlaceholderMessage.visibility = View.GONE
            movieListView.visibility = View.GONE

            moviesInteractor.searchMovies(
                queryMovieInput.text.toString(),
                object : MoviesInteractor.MoviesConsumer {
                    override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                        handler.post {
                            if (foundMovies != null) {
                                movieList.clear()
                                movieList.addAll(foundMovies)
                                movieListView.visibility = View.VISIBLE
                                adapter.notifyDataSetChanged()
                                if (movieList.isEmpty()) {
                                    showMessage(activity.getString(R.string.nothing_found), "")
                                } else {
                                    hideMessage()
                                }
                            }
                            if (errorMessage != null) {
                                showMessage(activity.getString(R.string.something_went_wrong), errorMessage)
                            }
                        }
                    }
                })
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            moviePlaceholderMessage.visibility = View.VISIBLE
            movieList.clear()
            adapter.notifyDataSetChanged()
            moviePlaceholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(activity, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            moviePlaceholderMessage.visibility = View.GONE
        }
    }

    private fun hideMessage() {
        moviePlaceholderMessage.visibility = View.GONE
    }
}