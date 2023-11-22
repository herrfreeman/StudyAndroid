package com.practicum.studyandroid

import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewHolderExample : AppCompatActivity() {

    companion object{
        val hamsterNames = listOf("Алиса", "Бимбо","Вжик","Дасти", "Китти", "Мафин")

        val hamsterContent = listOf("Говоря об отличии сирийских хомяков от обычных джунгариков, Марина Олеговна отмечает, что особой разницы, кроме размера, нет",
            "Помимо сбалансированного корма, который можно купить в зоомагазине, в рацион следует включать свежую траву, сено, овощи, фрукты.",
            "Регулярно следует чистить вольер, менять подстилку.",
            "Хомяки довольно активные животные. Для того чтобы животным было комфортно, в клетке, вольере следует установить различные приспособления.",
            "Оптимальная для содержания хомяков температура воздуха — 20–24С.",
            "Выпускать хомяков из клетки побегать по комнате можно, но при условии, что животное ручное.")
    }
    private fun getRandomName(): String = hamsterNames[(0..5).random()]

    private fun getRandomContent(): String = hamsterContent[(0..5).random()]

    private fun getRandomIsTop(): Boolean = (0..10).random() > 8
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_holder_example)

        val recycler = findViewById<RecyclerView>(R.id.exampleRecyclerView)
//        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false )
//        layoutManager определен в разметке

        recycler.adapter = NewsAdapter(
            news = List(100) {
                News(it,getRandomName(),getRandomContent(),getRandomIsTop())
            }
//            ,
//            onItemClicked = {
//
//            }
        )

    }
}

data class News(val id: Int, val name: String, val content: String, val isTopNews: Boolean)
class NewsViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

    private val sourceName: TextView
    private val text: TextView
    init {
        sourceName = parentView.findViewById(R.id.sourceName)
        text = parentView.findViewById(R.id.text)
    }

    fun bind(model: News) {
        // присваиваем в TextView значения из нашей модели
        sourceName.text = model.name
        text.text = model.content
        if (model.isTopNews) {
            sourceName.style
            sourceName.setTextColor(rgb(255,0,0))
        } else {
            sourceName.setTextColor(rgb(0,0,0))
        }
    }
}

class NewsAdapter(val news: List<News>) : RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.viewholder_item, parent, false)

        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.count()
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

}