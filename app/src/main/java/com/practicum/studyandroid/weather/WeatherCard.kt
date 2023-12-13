package com.practicum.studyandroid.weather

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.practicum.studyandroid.R

data class WeatherCard(
    val topView: View,
    val backgroundImage: ImageView,
    val winterImageLink: String,
    val summerImageLink: String,
    val dayName: TextView,
    val cloud: ImageView,
    val morningTemperature: TextView,
    val dayTemperature: TextView,
    val eveningTemperature: TextView,
    val morningTempName: String,
    val dayTempName: String,
    val eveningTempName: String
) {
    fun setValues(weatherItem: Weather) {
        dayName.setText(weatherItem.dayNameFull)
        val morningTemp = weatherItem.temperature - 10
        morningTemperature.setText("$morningTempName: ${if (morningTemp > 0) "+" else ""}${morningTemp}°C")
        dayTemperature.setText("$dayTempName: ${if (weatherItem.temperature > 0) "+" else ""}${weatherItem.temperature}°C")
        val eveningTemp = weatherItem.temperature - 5
        eveningTemperature.setText("$eveningTempName: ${if (eveningTemp > 0) "+" else ""}${eveningTemp}°C")

        cloud.setImageResource(weatherItem.imageResource)


        if (weatherItem.temperature > 3)
            Glide.with(topView)
                .load(summerImageLink)
                .centerCrop()
                .placeholder(R.drawable.weather_placeholder)
                .into(backgroundImage)
        else if (weatherItem.temperature < -3)
            Glide.with(topView)
                .load(winterImageLink)
                .centerCrop()
                .placeholder(R.drawable.weather_placeholder)
                .into(backgroundImage)
        else backgroundImage.setImageResource(R.drawable.weather_placeholder)

        topView.setBackgroundColor(
            Color.argb(
                50,
                Color.red(weatherItem.temperatureColor),
                Color.green(weatherItem.temperatureColor),
                Color.blue(weatherItem.temperatureColor)
            )
        )
    }

    fun clear() {
        dayName.setText("")
        morningTemperature.setText("")
        dayTemperature.setText("")
        eveningTemperature.setText("")
        cloud.setImageResource(R.drawable.empty)
        topView.setBackgroundColor(Color.WHITE)
    }
}