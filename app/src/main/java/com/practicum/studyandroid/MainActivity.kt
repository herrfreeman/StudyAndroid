package com.practicum.studyandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.practicum.studyandroid.imdb.MovieSearch
import com.practicum.studyandroid.weather.WeatherLocation

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

        findViewById<Button>(R.id.weather)
            .setOnClickListener {
                //startActivity(Intent(this, WeatherActivity::class.java))
                startActivity(Intent(this, WeatherLocation::class.java))
            }

        findViewById<Button>(R.id.movies)
            .setOnClickListener {
                startActivity(Intent(this, MovieSearch::class.java))
            }

        findViewById<Button>(R.id.rellayout)
            .setOnClickListener {
                startActivity(Intent(this, RelativeExample::class.java))
            }
    }
}