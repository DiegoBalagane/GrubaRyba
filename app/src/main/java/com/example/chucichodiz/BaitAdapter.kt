package com.example.chucichodiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BaitAdapter(private val context: Context, private val baits: List<Bait>)
    : RecyclerView.Adapter<BaitAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bait, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bait = baits[position]
        holder.bind(bait)
    }

    override fun getItemCount() = baits.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bait: Bait) {
            itemView.findViewById<TextView>(R.id.tbName).text = bait.name
            itemView.findViewById<ImageView>(R.id.ibPhoto).setImageResource(bait.photo)
        }
    }
}