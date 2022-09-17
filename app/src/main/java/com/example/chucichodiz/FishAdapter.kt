package com.example.chucichodiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FishAdapter(private val context: Context, private val fishes: List<Fish>)
    : RecyclerView.Adapter<FishAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_fish, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fish = fishes[position]
        holder.bind(fish)
    }

    override fun getItemCount() = fishes.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fish: Fish) {
            itemView.findViewById<TextView>(R.id.tvName).text = fish.name
            itemView.findViewById<ImageView>(R.id.ivPhoto).setImageResource(fish.photo)
        }
    }
}