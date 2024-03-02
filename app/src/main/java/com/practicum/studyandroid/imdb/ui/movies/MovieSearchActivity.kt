package com.practicum.studyandroid.imdb.ui.movies


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.practicum.studyandroid.R
import com.practicum.studyandroid.databinding.ActivityMoviesearchBinding
import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.util.Creator
import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.presentation.movies.MoviesView


class MovieSearchActivity : AppCompatActivity(), MoviesView {

    private lateinit var bindings: ActivityMoviesearchBinding
    private val adapter = MovieAdapter()
    private val moviesSearchPresenter = Creator.provideMoviesSearchPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindings = ActivityMoviesearchBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        bindings.movieList.adapter = adapter
        bindings.searchMovieButton.setOnClickListener{ moviesSearchPresenter.searchRequest(bindings.queryMovieInput.text.toString()) }

    }

    override fun setPlaceholderVisibility(isVisible: Boolean) {
        bindings.moviePlaceholderMessage.isVisible = isVisible
    }

    override fun setMoviesListVisibility(isVisible: Boolean) {
        bindings.movieList.isVisible = isVisible
    }

    override fun setPlaceholderText(text: String) {
        bindings.moviePlaceholderMessage.text = text
    }

    override fun updateMovieList(newMoviesList: List<Movie>) {
        adapter.movies.clear()
        adapter.movies.addAll(newMoviesList)
        adapter.notifyDataSetChanged()
    }

    override fun showInstantMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}

