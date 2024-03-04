package com.practicum.studyandroid.imdb.ui.movies


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.practicum.studyandroid.R
import com.practicum.studyandroid.StudyApplication
import com.practicum.studyandroid.databinding.ActivityMoviesearchBinding
import com.practicum.studyandroid.imdb.domain.api.MoviesInteractor
import com.practicum.studyandroid.imdb.util.Creator
import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.presentation.movies.MoviesSearchPresenter
import com.practicum.studyandroid.imdb.presentation.movies.MoviesView
import com.practicum.studyandroid.imdb.ui.movies.models.MoviesState
import moxy.MvpActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class MovieSearchActivity : MvpActivity(), MoviesView {

    private lateinit var bindings: ActivityMoviesearchBinding
    private val adapter = MovieAdapter()

    @InjectPresenter
    lateinit var moviesSearchPresenter: MoviesSearchPresenter

    @ProvidePresenter
    fun providePresenter(): MoviesSearchPresenter {
        return Creator.provideMoviesSearchPresenter(
            context = this.applicationContext,
        )
    }

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            moviesSearchPresenter?.searchDebounce(s.toString())
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindings = ActivityMoviesearchBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        bindings.movieList.adapter = adapter
        //bindings.searchMovieButton.setOnClickListener{ moviesSearchPresenter.searchRequest(bindings.queryMovieInput.text.toString()) }
        bindings.searchMovieButton.setOnClickListener{ moviesSearchPresenter?.searchNow(bindings.queryMovieInput.text.toString()) }

        bindings.queryMovieInput.addTextChangedListener(textWatcher)

    }

    override fun onDestroy() {
        super.onDestroy()

        textWatcher?.let { bindings.queryMovieInput.removeTextChangedListener(it) }
    }

    override fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Empty -> showEmpty(state.message)
        }
    }

    override fun showInstantMessage(text: String) {
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

}

