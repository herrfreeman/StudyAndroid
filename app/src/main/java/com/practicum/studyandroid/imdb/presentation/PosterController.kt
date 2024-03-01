package com.practicum.studyandroid.imdb.presentation

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.practicum.studyandroid.R

class PosterController(private val activity: Activity) {

    private lateinit var poster: ImageView

    fun onCreate() {
        poster = activity.findViewById(R.id.movie_poster)
        val url = activity.intent.extras?.getString("imagePath", "")

        Glide.with(activity.applicationContext)
            .load(url)
            .into(poster)
    }
}