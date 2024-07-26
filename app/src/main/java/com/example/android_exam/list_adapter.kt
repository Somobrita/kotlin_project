package com.example.android_exam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val items: List<Item>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.itemImageView)
        val title: TextView = view.findViewById(R.id.itemTitle)
        val subtitle: TextView = view.findViewById(R.id.itemSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageRes)
        holder.title.text = item.title
        holder.subtitle.text = item.subtitle
    }

    override fun getItemCount(): Int = items.size
}

data class Item(val imageRes: Int, val title: String, val subtitle: String)
