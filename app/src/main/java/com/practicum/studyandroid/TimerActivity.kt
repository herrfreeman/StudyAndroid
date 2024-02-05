package com.practicum.studyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.practicum.studyandroid.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity() {

    lateinit var binding: ActivityTimerBinding
    lateinit var handler: Handler
    var timer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        handler = Handler(Looper.getMainLooper())

        setContentView(binding.root)

        binding.startButton.setOnClickListener {

            timer = binding.timerInput.text.toString().toInt()
            updateTimer()

            handler.postDelayed( object : Runnable {
                override fun run() {
                    if (--timer == 0) {
                        binding.timerText.text = "DONE!"
                    } else {
                        updateTimer()
                        handler.postDelayed(this, 1000)
                    }
                }
            }, 1000
            )
        }
    }

    fun updateTimer() {
        binding.timerText.text = timer.toString()
    }
}