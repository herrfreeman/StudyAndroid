package com.practicum.studyandroid

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Math
import kotlin.math.roundToLong

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val dayMap = mapOf<Int, String>(
            1 to getString(R.string.monday_short),
            2 to getString(R.string.tuesday_short),
            3 to getString(R.string.wednesday_short),
            4 to getString(R.string.thursday_short),
            5 to getString(R.string.friday_short),
            6 to getString(R.string.saturday_short),
            0 to getString(R.string.Sunday_short)
        )

        val weatherList: MutableList<Weather> = mutableListOf()
        var previousWeather: Weather? = null
        for (i in 1..100) {
            val minTemp = Math.max((previousWeather?.temperature ?: -20) - 10, -30)
            val maxTemp = Math.min((previousWeather?.temperature ?: 20) + 10, 30)

            weatherList.add(
                Weather(
                    dayMap[i % 7]!!,
                    (i % 7) in intArrayOf(0,6),
                    (1..10).random() > 7, //70% not clear
                    (minTemp..maxTemp).random()
                )
            )

            previousWeather = weatherList.last()
        }


        val recycler = findViewById<RecyclerView>(R.id.weather_recycler_view)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = WeatherAdapter(weatherList)

    }
}

data class Weather(val dayName: String, val isWeekend: Boolean, val isClear: Boolean, val temperature: Int)

class WeatherViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

    private val dayName: TextView
    private val cloudImage: ImageView
    private val temperature: TextView
    private val weatherBox: LinearLayout

    init {
        dayName = parentView.findViewById<TextView>(R.id.day_name)
        cloudImage = parentView.findViewById(R.id.cloud)
        temperature = parentView.findViewById(R.id.temperature)
        weatherBox = parentView.findViewById(R.id.weather_box)
    }

    fun bind(model: Weather) {
        dayName.text = model.dayName
        if (model.isWeekend) {
            dayName.setTypeface(null, Typeface.BOLD)
            //dayName.setTextColor(Color.RED)
        } else {
            dayName.setTypeface(null, Typeface.NORMAL)
            //dayName.setTextColor(Color.BLACK)
        }

        temperature.text = "${if (model.temperature > 0) "+" else ""}${model.temperature}°C"
        cloudImage.setImageResource(
            if (model.isClear) {
                R.drawable.sunny
            } else {
                when {
                    model.temperature < -5 -> R.drawable.weather_snowy
                    model.temperature > 5 -> R.drawable.rainy
                    else -> R.drawable.weather_mix
                }
            }
        )

        if (model.temperature > 3) {

            weatherBox.setBackgroundColor(
                calculateColors(
                    "#FFA588".toColorInt(),
                    "#FF5722".toColorInt(),
                    (model.temperature-3) / 30F
                )
            )

        } else if (model.temperature < -3) {
            weatherBox.setBackgroundColor(
                calculateColors(
                    "#A0D5FF".toColorInt(),
                    "#008EFF".toColorInt(),
                    (-model.temperature+3) / 30F
                )
            )
        } else {
            weatherBox.setBackgroundColor(Color.WHITE)
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

fun calculateColors(startColor: Int, endColor: Int, position: Float): Int {
    val red = calculateColor(Color.red(startColor), Color.red(endColor), position)
    val green = calculateColor(Color.green(startColor), Color.green(endColor), position)
    val blue = calculateColor(Color.blue(startColor), Color.blue(endColor), position)

    return Color.rgb(red, green, blue)
}

fun calculateColor(start: Int, end: Int, position: Float): Int {
    val calc = start + ((end - start) * position).toInt()

    return when {
        calc > 255 -> 255
        calc < 0 -> 0
        else -> calc
    }

}