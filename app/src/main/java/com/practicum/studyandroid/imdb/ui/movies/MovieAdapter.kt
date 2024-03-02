package com.practicum.studyandroid.imdb.ui.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.studyandroid.imdb.domain.models.Movie

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    val movies: MutableList<Movie> = emptyList<Movie>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(parent)

    override fun getItemCount(): Int = movies.count()

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

}