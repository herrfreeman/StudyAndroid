package com.practicum.studyandroid.imdb.ui.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.studyandroid.R
import com.practicum.studyandroid.imdb.domain.models.Movie

class MovieViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.movie_item, parentView, false)
) {


    private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
    private val movieDesription: TextView = itemView.findViewById(R.id.movie_description)
    private val movieImage: ImageView = itemView.findViewById(R.id.movie_image)

    fun bind(model: Movie) {
        movieTitle.text = model.title
        movieDesription.text = model.description
        Glide.with(itemView)
            .load(model.image)
            .centerCrop()
            .into(movieImage)
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, MoviePosterActivity::class.java)
            intent.putExtra("imagePath", model.image)
            itemView.context.startActivity(intent)
        }
    }
}