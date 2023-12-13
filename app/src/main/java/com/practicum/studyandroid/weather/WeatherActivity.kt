package com.practicum.studyandroid.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.studyandroid.R
import java.lang.Integer.min
import java.lang.Math

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val weatherCard = WeatherCard(
            topView = findViewById(R.id.top_view),
            backgroundImage = findViewById(R.id.weather_background),
            winterImageLink = getString(R.string.winter_image_link),
            summerImageLink = getString(R.string.summer_image_link),
            dayName = findViewById(R.id.daycard_dayname),
            cloud = findViewById(R.id.daycard_cloud),
            morningTemperature = findViewById(R.id.daycard_morning_temperature),
            dayTemperature = findViewById(R.id.daycard_day_temperature),
            eveningTemperature = findViewById(R.id.daycard_evening_temperature),
            morningTempName = getString(R.string.morning_caption),
            dayTempName = getString(R.string.afternoon_caption),
            eveningTempName = getString(R.string.evening_caption)
        )


        val dayMapShort = mapOf<Int, String>(
            1 to getString(R.string.monday_short),
            2 to getString(R.string.tuesday_short),
            3 to getString(R.string.wednesday_short),
            4 to getString(R.string.thursday_short),
            5 to getString(R.string.friday_short),
            6 to getString(R.string.saturday_short),
            0 to getString(R.string.sunday_short)
        )
        val dayMapFull = mapOf<Int, String>(
            1 to getString(R.string.monday),
            2 to getString(R.string.tuesday),
            3 to getString(R.string.wednesday),
            4 to getString(R.string.thursday),
            5 to getString(R.string.friday),
            6 to getString(R.string.saturday),
            0 to getString(R.string.sunday)
        )



        val weatherList: MutableList<Weather> = mutableListOf()
        var previousWeather: Weather? = null
        for (i in 1..100) {
            val minTemp = Math.max((previousWeather?.temperature ?: -20) - 10, -30)
            val maxTemp = Math.min((previousWeather?.temperature ?: 20) + 10, 30)

            weatherList.add(
                Weather(
                    dayMapShort[i % 7]!!,
                    dayMapFull[i % 7]!!,
                    (i % 7) in intArrayOf(0, 6),
                    (1..10).random() > 7, //70% not clear
                    (minTemp..maxTemp).random()
                )
            )

            previousWeather = weatherList.last()
        }
        if (weatherList.isNotEmpty()) weatherCard.setValues(weatherList.first())

        val recycler = findViewById<RecyclerView>(R.id.weather_recycler_view)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = WeatherAdapter(weatherList, weatherCard) {
            weatherList.remove(weatherList[it])
            if (weatherList.isNotEmpty())
                weatherCard.setValues(weatherList[min(it, weatherList.size - 1)])
            else weatherCard.clear()

            recycler.adapter?.notifyItemRemoved(it)
            recycler.adapter?.notifyItemRangeChanged(it, weatherList.count())
        }

    }

}


