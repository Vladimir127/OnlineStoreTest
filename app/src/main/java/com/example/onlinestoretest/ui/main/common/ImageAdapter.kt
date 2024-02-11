package com.example.onlinestoretest.ui.main.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinestoretest.R
import com.squareup.picasso.Picasso

class ImageAdapter() : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var images: List<Int> = emptyList()

    fun setData(images: List<Int>) {
        this.images = images
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageResourceId = images[position]
        //holder.imageView.setImageResource(imageResource)
        Picasso.get().load(imageResourceId).into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
