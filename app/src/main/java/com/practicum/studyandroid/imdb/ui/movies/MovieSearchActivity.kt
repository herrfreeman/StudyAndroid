package com.practicum.studyandroid.imdb.ui.movies


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.practicum.studyandroid.R
import com.practicum.studyandroid.databinding.ActivityMoviesearchBinding
import com.practicum.studyandroid.imdb.Creator
import com.practicum.studyandroid.imdb.data.dto.MovieSearchResponse
import com.practicum.studyandroid.imdb.data.network.ImdbApi
import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.domain.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieSearchActivity : AppCompatActivity() {

    private val moviesInteractor = Creator.provideMoviesInteractor()
    private val handler = Handler(Looper.getMainLooper())

    private val movieList: MutableList<Movie> = emptyList<Movie>().toMutableList()
    private lateinit var bindings: ActivityMoviesearchBinding

    val adapter = MovieAdapter(movieList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindings = ActivityMoviesearchBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        bindings.movieList.adapter = adapter

        bindings.searchMovieButton.setOnClickListener {
            if (bindings.queryMovieInput.text.isNotEmpty()) {

                bindings.moviePlaceholderMessage.visibility = View.GONE
                bindings.movieList.visibility = View.GONE

                moviesInteractor.searchMovies(bindings.queryMovieInput.text.toString(), object : MoviesInteractor.MoviesConsumer {
                    override fun consume(foundMovies: List<Movie>) {
                        handler.post {
                            movieList.clear()
                            movieList.addAll(foundMovies)
                            bindings.movieList.visibility = View.VISIBLE
                            adapter.notifyDataSetChanged()
                            if (movieList.isEmpty()) {
                                showMessage(getString(R.string.nothing_found), "")
                            } else {
                                hideMessage()
                            }
                        }
                    }
                })
            }


        }

    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            bindings.moviePlaceholderMessage.visibility = View.VISIBLE
            movieList.clear()
            adapter.notifyDataSetChanged()
            bindings.moviePlaceholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            bindings.moviePlaceholderMessage.visibility = View.GONE
        }
    }

    private fun hideMessage() {
        bindings.moviePlaceholderMessage.visibility = View.GONE
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}

