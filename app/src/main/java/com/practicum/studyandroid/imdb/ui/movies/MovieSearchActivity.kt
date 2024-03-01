package com.practicum.studyandroid.imdb.ui.movies


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practicum.studyandroid.databinding.ActivityMoviesearchBinding
import com.practicum.studyandroid.imdb.util.Creator
import com.practicum.studyandroid.imdb.domain.models.Movie


class MovieSearchActivity : AppCompatActivity() {

    private val movieList: MutableList<Movie> = emptyList<Movie>().toMutableList()
    private lateinit var bindings: ActivityMoviesearchBinding

    private val adapter = MovieAdapter(movieList)

    private val moviesSearchController = Creator.provideMoviesSearchController(this, adapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindings = ActivityMoviesearchBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        bindings.movieList.adapter = adapter

        moviesSearchController.onCreate()
    }

}

