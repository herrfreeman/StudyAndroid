package com.practicum.studyandroid.imdb.presentation.poster

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.practicum.studyandroid.R

class PosterPresenter(private val view: PosterView, private val internalParams: Bundle?) {

    fun onCreate() {
        internalParams?.apply {
            view.setPosterImage(this.getString("imagePath", ""))
        }
    }
}