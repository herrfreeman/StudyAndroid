package com.practicum.studyandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.SpinnerAdapter
import kotlin.random.Random


class ListExamples : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_examples)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val names = listOf("Константин", "Леонид", "Макар", "Пракоп", "Тимофей")
        val adapter: SpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, names
        )
        spinner.adapter = adapter


//        val gridView = findViewById<GridView>(R.id.gridView)
        val hamsterNames = List(100) {
            names[Random.nextInt(1, 5)]
        }

        val listView = findViewById<ListView>(R.id.listView)
        val listAdapter: ListAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, hamsterNames
        )
        listView.adapter = listAdapter

//        val gridView = findViewById<GridView>(R.id.gridView)
//        val gridAdapter: ListAdapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_1, hamsterNames
//        )
//        gridView.adapter = gridAdapter

    }


}