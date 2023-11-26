package com.practicum.studyandroid

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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


class WeatherAdapter(
    val weatherList: List<Weather>,
    val weatherCard: WeatherCard,
    val deleteAction: (Int) -> Unit
) :
    RecyclerView.Adapter<WeatherViewHolder>() {
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

