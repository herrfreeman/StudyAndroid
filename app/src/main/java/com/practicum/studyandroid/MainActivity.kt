package com.practicum.studyandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.practicum.studyandroid.imdb.ui.movies.MovieSearchActivity
import com.practicum.studyandroid.player.presentation.ui.TrackActivity
import com.practicum.studyandroid.weather.WeatherActivity
import com.practicum.studyandroid.weather.WeatherLocation

fun interface ClickListener {
    fun onClick()
}

data class ActionItem(val name: String, val clickListener: ClickListener)

class MainActivity : AppCompatActivity() {

    val actionList = mutableListOf<ActionItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.mainRecyclerView)
        recycler.adapter = MainViewHolderAdapter(actionList)

        actionList.add(ActionItem("Prime numbers") {startActivity(Intent(this, PrimeNumber::class.java))})
        actionList.add(ActionItem("List example") {startActivity(Intent(this, ListExamples::class.java))})
        actionList.add(ActionItem("ViewHolder example") {startActivity(Intent(this, ViewHolderExample::class.java))})

        actionList.add(ActionItem("ViewHolder example") {startActivity(Intent(this, ViewHolderExample::class.java))})
        actionList.add(ActionItem("Weather") {startActivity(Intent(this, WeatherActivity::class.java))})
        actionList.add(ActionItem("Weather location API") {startActivity(Intent(this, WeatherLocation::class.java))})
        actionList.add(ActionItem("RelativeLayout example") {startActivity(Intent(this, RelativeExample::class.java))})
        actionList.add(ActionItem("ConstraintLayout example") {startActivity(Intent(this, ConstraintLayoutExample::class.java))})
        actionList.add(ActionItem("ConstraintLayout homework") {startActivity(Intent(this, ConstraintLayoutHomework::class.java))})
        actionList.add(ActionItem("Timer") {startActivity(Intent(this, TimerActivity::class.java))})
        actionList.add(ActionItem("IMDB API (MVVM)") {startActivity(Intent(this, MovieSearchActivity::class.java))})
        actionList.add(ActionItem("Track (MVVM)") {startActivity(Intent(this, TrackActivity::class.java))})

    }
}

class MainViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parentView.context)
    .inflate(R.layout.main_recycler_element, parentView, false)) {

    var button: Button

    init {
        button = itemView.findViewById(R.id.runButton)
    }

    fun bind(model: ActionItem) {
        button.text = model.name
        button.setOnClickListener { model.clickListener.onClick() }
    }
}


class MainViewHolderAdapter(val actionList: List<ActionItem>) : RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder = MainViewHolder(parent)
    override fun getItemCount(): Int = actionList.size
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(actionList[position])
    }
}