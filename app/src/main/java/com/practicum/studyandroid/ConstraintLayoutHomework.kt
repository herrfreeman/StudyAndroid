package com.practicum.studyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

class ConstraintLayoutHomework : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout_homework)

        var myLayout = findViewById<ConstraintLayout>(R.id.myLayout)
        var myButton = findViewById<ImageView>(R.id.myBackButton)
        var originalLayout = findViewById<ConstraintLayout>(R.id.originalLayout)
        var originalButton = findViewById<ImageView>(R.id.backButton)

        myButton.setOnClickListener {
            myLayout.isVisible = false
            originalLayout.isVisible = !myLayout.isVisible
        }

        originalButton.setOnClickListener {
            myLayout.isVisible = true
            originalLayout.isVisible = !myLayout.isVisible
        }

    }
}