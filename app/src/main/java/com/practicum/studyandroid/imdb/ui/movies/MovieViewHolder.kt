package com.practicum.studyandroid.imdb.ui.movies

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.studyandroid.R
import com.practicum.studyandroid.imdb.domain.models.Movie
import com.practicum.studyandroid.imdb.ui.poster.MoviePosterActivity

class MovieViewHolder(
    parentView: ViewGroup,
    private val clickListener: MovieAdapter.MovieClickListener,
    ) : RecyclerView.ViewHolder(

    LayoutInflater.from(parentView.context)
        .inflate(R.layout.movie_item, parentView, false)
) {


    private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
    private val movieDesription: TextView = itemView.findViewById(R.id.movie_description)
    private val movieImage: ImageView = itemView.findViewById(R.id.movie_image)
    private val inFavoriteToggle: ImageView = itemView.findViewById(R.id.favoriteButton)

    fun bind(model: Movie) {
        movieTitle.text = model.title
        movieDesription.text = model.description
        Glide.with(itemView)
            .load(model.image)
            .centerCrop()
            .into(movieImage)
        itemView.setOnClickListener {
//            val intent = Intent(itemView.context, MoviePosterActivity::class.java)
//            intent.putExtra("imagePath", model.image)
//            itemView.context.startActivity(intent)
//            clickListener(model)
            clickListener.onMovieClick(model)
        }
        inFavoriteToggle.setOnClickListener { clickListener.onFavoriteToggleClick(model)}

        inFavoriteToggle.setImageDrawable(getFavoriteToggleDrawable(model.inFavorite))
    }

    private fun getFavoriteToggleDrawable(inFavorite: Boolean): Drawable? {
        return itemView.context.getDrawable(
            if(inFavorite) R.drawable.favorite_filled else R.drawable.favorite_unfilled
        )
    }
}