package com.practicum.studyandroid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Math

class WeatherActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)


        val dayMap = mapOf<Int, String>(1 to getString(R.string.monday_short),
            2 to getString(R.string.tuesday_short),
            3 to getString(R.string.wednesday_short),
            4 to getString(R.string.thursday_short),
            5 to getString(R.string.friday_short),
            6 to getString(R.string.saturday_short),
            0 to getString(R.string.Sunday_short))

        val weatherList: MutableList<Weather> = mutableListOf()
        var previousWeather: Weather? = null
        for (i in 1..100) {
            val minTemp = Math.max((previousWeather?.temperature ?: -20) - 10, -30)
            val maxTemp = Math.min((previousWeather?.temperature ?: 20) + 10, 30)

            weatherList.add(Weather(dayMap[i%7]!!,
                (1..10).random()>7, //70% not clear
                (minTemp..maxTemp).random()))

            previousWeather = weatherList.last()
        }



        val recycler = findViewById<RecyclerView>(R.id.weather_recycler_view)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false )
        recycler.adapter = WeatherAdapter(weatherList)

    }
}

data class Weather(val dayName: String, val isClear: Boolean, val temperature: Int)

class WeatherViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

    private val dayName: TextView
    private val cloudImage: ImageView
    private val temperature: TextView
    private val weatherBox: LinearLayout

    init {
        dayName = parentView.findViewById(R.id.day_name)
        cloudImage = parentView.findViewById(R.id.cloud)
        temperature = parentView.findViewById(R.id.temperature)
        weatherBox = parentView.findViewById(R.id.weather_box)
    }

    fun bind(model: Weather) {
        dayName.text = model.dayName
        temperature.text = "${if (model.temperature > 0) "+" else ""}${model.temperature}°C"
        cloudImage.setImageResource(
            if (model.isClear) {
                R.drawable.sunny
            } else {
                when {
                    model.temperature < -10 -> R.drawable.weather_snowy
                    model.temperature > 5 -> R.drawable.rainy
                    else -> R.drawable.weather_mix
                }
            }
        )

        if (model.temperature > 0) {
            weatherBox.setBackgroundColor(Color.rgb(255,
                165-(model.temperature*2.6).toInt(),
                136-(model.temperature*3.4).toInt()))
        } else if (model.temperature < 0) {
            weatherBox.setBackgroundColor(Color.rgb(160-(-model.temperature * 5.3).toInt(),
                213 - (-model.temperature*2.5).toInt(),
                255))
        } else {
            weatherBox.setBackgroundColor(Color.rgb(255,255,255))
        }
    }
}

class WeatherAdapter(val weatherList: List<Weather>) : RecyclerView.Adapter<WeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.weather_item, parent, false)

        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherList.count()
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

}