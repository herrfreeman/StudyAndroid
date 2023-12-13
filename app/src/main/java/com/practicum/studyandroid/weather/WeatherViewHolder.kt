package com.practicum.studyandroid.weather

import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicum.studyandroid.R

class WeatherViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

    private val dayName: TextView
    private val cloudImage: ImageView
    private val temperature: TextView
    private val weatherBox: LinearLayout
    //private val weatherBackgroud: ImageView
    init {
        dayName = parentView.findViewById(R.id.day_name)
        cloudImage = parentView.findViewById(R.id.cloud)
        temperature = parentView.findViewById(R.id.temperature)
        weatherBox = parentView.findViewById(R.id.weather_box)
        //weatherBackgroud = parentView.findViewById(R.id.weather_background)
    }

    fun bind(model: Weather) {
        dayName.text = model.dayNameShort
        if (model.isWeekend) {
            dayName.setTypeface(null, Typeface.BOLD)
            //dayName.setTextColor(Color.RED)
        } else {
            dayName.setTypeface(null, Typeface.NORMAL)
            //dayName.setTextColor(Color.BLACK)
        }
        temperature.text = "${if (model.temperature > 0) "+" else ""}${model.temperature}°C"

        cloudImage.setImageResource(model.imageResource)
        weatherBox.setBackgroundColor(model.temperatureColor)
    }
}