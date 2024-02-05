package com.practicum.studyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.practicum.studyandroid.databinding.ActivityMovieDescriptionBinding

class MovieDescription : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMovieDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load(intent.extras?.getString("imagePath"))
            .centerCrop()
            .into(binding.moviePoster)

//        val handler = Handler(Looper.getMainLooper())
//        handler.removeCallbacks()
    }
}