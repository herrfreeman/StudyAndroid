package com.practicum.studyandroid.imdb.ui.movies


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.practicum.studyandroid.databinding.ActivityMoviesearchBinding
import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.presentation.movies.MoviesSearchViewModel
import com.practicum.studyandroid.imdb.ui.movies.models.MoviesState
import com.practicum.studyandroid.imdb.ui.poster.MoviePosterActivity


class MovieSearchActivity : ComponentActivity() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private lateinit var bindings: ActivityMoviesearchBinding
    private val adapter = MovieAdapter(
        object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                if (clickDebounce()) {
                    val intent = Intent(this@MovieSearchActivity, MoviePosterActivity::class.java)
                    intent.putExtra("imagePath", movie.image)
                    startActivity(intent)
                }
            }

            override fun onFavoriteToggleClick(movie: Movie) {
                viewModel.toggleFavorite(movie)
            }
        }
    )

    lateinit var viewModel: MoviesSearchViewModel
    lateinit var textWatcher: TextWatcher
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindings = ActivityMoviesearchBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        bindings.movieList.adapter = adapter

        viewModel = ViewModelProvider(this, MoviesSearchViewModel.getViewModelFactory())[MoviesSearchViewModel::class.java]
        viewModel.observeState().observe(this) {render(it)}
        viewModel.observeToastState().observe(this) {showToast(it)}

        bindings.searchMovieButton.setOnClickListener{ viewModel?.searchNow(bindings.queryMovieInput.text.toString()) }

        textWatcher = object : TextWatcher{
            override fun beforeTextChanged(
                s: kotlin.CharSequence?,
                start: kotlin.Int,
                count: kotlin.Int,
                after: kotlin.Int
            ) {}

            override fun onTextChanged(s: kotlin.CharSequence?, start: kotlin.Int, before: kotlin.Int, count: kotlin.Int) {}

            override fun afterTextChanged(s: android.text.Editable?) {
                viewModel?.searchDebounce(s.toString())
            }

        }

        bindings.queryMovieInput.addTextChangedListener(textWatcher)

    }

    override fun onDestroy() {
        super.onDestroy()

        textWatcher?.let { bindings.queryMovieInput.removeTextChangedListener(it) }
    }

    fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Empty -> showEmpty(state.message)
        }
    }

    fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    fun showLoading() {
        bindings.movieList.visibility = View.GONE
        bindings.moviePlaceholderMessage.visibility = View.GONE
        //bindings.progressBar.visibility = View.VISIBLE
    }

    fun showError(errorMessage: String) {
        bindings.movieList.visibility = View.GONE
        bindings.moviePlaceholderMessage.visibility = View.VISIBLE
        //bindings.progressBar.visibility = View.GONE

        bindings.moviePlaceholderMessage.text = errorMessage
    }

    fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    fun showContent(movies: List<Movie>) {
        bindings.movieList.visibility = View.VISIBLE
        bindings.moviePlaceholderMessage.visibility = View.GONE
        //bindings.progressBar.visibility = View.GONE

        adapter.movies.clear()
        adapter.movies.addAll(movies)
        adapter.notifyDataSetChanged()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }


}



