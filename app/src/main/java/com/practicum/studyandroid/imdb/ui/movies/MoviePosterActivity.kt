package com.practicum.studyandroid.imdb.ui.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practicum.studyandroid.databinding.ActivityMoviePosterBinding
import com.practicum.studyandroid.imdb.util.Creator


class MoviePosterActivity : AppCompatActivity() {

    private val posterController = Creator.providePosterController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMoviePosterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        posterController.onCreate()
    }
}