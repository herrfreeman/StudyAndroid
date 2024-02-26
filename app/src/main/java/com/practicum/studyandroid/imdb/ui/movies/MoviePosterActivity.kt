package com.practicum.studyandroid.imdb.ui.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.practicum.studyandroid.databinding.ActivityMoviePosterBinding


class MoviePosterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMoviePosterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load(intent.extras?.getString("imagePath"))
            .centerCrop()
            .into(binding.moviePoster)

//        val handler = Handler(Looper.getMainLooper())
//        handler.removeCallbacks()
    }
}