package com.practicum.studyandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startPrimeNumbers)
        .setOnClickListener {
            startActivity(Intent(this, PrimeNumber::class.java))
        }

        findViewById<Button>(R.id.startListExample)
            .setOnClickListener {
                startActivity(Intent(this, ListExamples::class.java))
            }

        findViewById<Button>(R.id.recycleViewExample)
            .setOnClickListener {
                startActivity(Intent(this, ViewHolderExample::class.java))
            }
    }
}