package com.practicum.studyandroid.player.presentation.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.practicum.studyandroid.databinding.TrackRecyclerItemBinding
import com.practicum.studyandroid.player.domain.TrackModel


class TrackRecyclerViewAdapter(val clickListener: (TrackModel) -> Unit): RecyclerView.Adapter<TrackRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return ViewHolder(TrackRecyclerItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder(private val binding: TrackRecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TrackModel, clickListener: (TrackModel) -> Unit) {
            binding.author.text = item.author
            binding.track.text = item.name
            binding.root.setOnClickListener{
                Log.d("TEST", item.author)
                clickListener(item)
            }
        }
    }

    fun getItem(position: Int) : TrackModel{
        return TrackModel( id = "1", author = "Author "+position, name = "Track "+position, pictureUrl = "")
    }
}