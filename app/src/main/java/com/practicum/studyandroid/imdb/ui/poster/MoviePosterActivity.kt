package com.practicum.studyandroid.imdb.ui.poster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.practicum.studyandroid.databinding.ActivityMoviePosterBinding
import com.practicum.studyandroid.imdb.presentation.poster.PosterPresenter
import com.practicum.studyandroid.imdb.presentation.poster.PosterView
import com.practicum.studyandroid.imdb.util.Creator


class MoviePosterActivity : AppCompatActivity(), PosterView {

    private lateinit var bindings: ActivityMoviePosterBinding
    private lateinit var posterPresenter: PosterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMoviePosterBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        posterPresenter = Creator.providePosterPresenter(this, intent.extras)
        posterPresenter.onCreate()
    }

    override fun setPosterImage(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(bindings.moviePoster)
    }
}