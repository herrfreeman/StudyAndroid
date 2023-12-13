package com.practicum.studyandroid.weather

import android.graphics.Color
import androidx.core.graphics.toColorInt
import com.practicum.studyandroid.R

data class Weather(
    val dayNameShort: String,
    val dayNameFull: String,
    val isWeekend: Boolean,
    val isClear: Boolean,
    val temperature: Int

) {
    val temperatureColor: Int = calculateTemperatureColor()
    val imageResource: Int = calculateImageResource()

    private fun calculateTemperatureColor(): Int {
        if (temperature > 3) {
            return calculateColors(
                "#FFA588".toColorInt(),
                "#FF5722".toColorInt(),
                (temperature - 3) / 30F
            )


        } else if (temperature < -3) {
            return calculateColors(
                "#A0D5FF".toColorInt(),
                "#008EFF".toColorInt(),
                (-temperature + 3) / 30F
            )

        } else {
            return "#FFFFFF".toColorInt()
        }
    }

    private fun calculateColors(startColor: Int, endColor: Int, position: Float): Int {
        val red = calculateColor(Color.red(startColor), Color.red(endColor), position)
        val green = calculateColor(Color.green(startColor), Color.green(endColor), position)
        val blue = calculateColor(Color.blue(startColor), Color.blue(endColor), position)

        return Color.rgb(red, green, blue)
    }

    private fun calculateColor(start: Int, end: Int, position: Float): Int {
        val calc = start + ((end - start) * position).toInt()

        return when {
            calc > 255 -> 255
            calc < 0 -> 0
            else -> calc
        }
    }

    private fun calculateImageResource() =
        if (isClear) {
            R.drawable.sunny
        } else {
            when {
                temperature < -5 -> R.drawable.weather_snowy
                temperature > 5 -> R.drawable.rainy
                else -> R.drawable.weather_mix
            }
        }
}