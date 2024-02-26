package com.practicum.studyandroid.imdb.ui.movies


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.practicum.studyandroid.R
import com.practicum.studyandroid.imdb.data.dto.MovieSearchResponse
import com.practicum.studyandroid.imdb.data.network.ImdbApi
import com.practicum.studyandroid.imdb.domain.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieSearchActivity : AppCompatActivity() {

    private val ImdbBaseUrl = "https://tv-api.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(ImdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(ImdbApi::class.java)
    private val movieList: MutableList<Movie> = emptyList<Movie>().toMutableList()


    private lateinit var searchButton: Button
    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView

    val adapter = MovieAdapter(movieList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moviesearch)

        placeholderMessage = findViewById(R.id.moviePlaceholderMessage)

        val recycler = findViewById<RecyclerView>(R.id.movieList)
        recycler.adapter = adapter

        searchButton = findViewById(R.id.searchMovieButton)
        queryInput = findViewById(R.id.queryMovieInput)
        searchButton.setOnClickListener {
            imdbService.getMovies(getString(R.string.imbd_api_key), queryInput.text.toString())
                .enqueue(object : Callback<MovieSearchResponse> {
                    override fun onResponse(
                        call: Call<MovieSearchResponse>,
                        response: Response<MovieSearchResponse>
                    ) {
                        val searchResponse = response.body()

                        if (searchResponse == null || searchResponse.errorMessage.isNotEmpty()) {
                            showMessage(
                                getString(R.string.something_went_wrong),
                                searchResponse?.errorMessage ?: ""
                            )
                        } else {
                            showMessage("", "")
                            movieList.clear()
                            movieList.addAll(
                                searchResponse?.results ?: emptyList<Movie>().toMutableList()
                            )
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                        showMessage(
                            getString(R.string.something_went_wrong), t.stackTraceToString()
                        )
                    }
                })
        }


    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            placeholderMessage.visibility = View.VISIBLE
            movieList.clear()
            adapter.notifyDataSetChanged()
            placeholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
        }
    }
}

