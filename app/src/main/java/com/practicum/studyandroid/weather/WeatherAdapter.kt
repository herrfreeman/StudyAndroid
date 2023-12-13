package com.practicum.studyandroid.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.studyandroid.R

class WeatherAdapter(
    val weatherList: List<Weather>,
    val weatherCard: WeatherCard,
    val deleteAction: (Int) -> Unit
) :
    RecyclerView.Adapter<WeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)

        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int = weatherList.count()

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
        holder.itemView.setOnClickListener {
            weatherCard.setValues(weatherList[position])
//            weatherCard.dayName.setText(weatherList[position].dayNameFull)
            //cardViews.cloud.setBackgroundColor(weatherList[position].temperatureColor)
        }
        holder.itemView.setOnLongClickListener {
            deleteAction(position)
            true
        }
    }

}