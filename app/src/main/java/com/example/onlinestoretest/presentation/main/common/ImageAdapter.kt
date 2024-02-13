package com.example.onlinestoretest.presentation.main.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinestoretest.R
import com.squareup.picasso.Picasso

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var onImageClickListener: OnImageClickListener? = null
    private var images: List<Int> = emptyList()

    fun setData(images: List<Int>) {
        this.images = images
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = images.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(position: Int) {
            val imageResourceId = images[position]
            Picasso.get().load(imageResourceId).into(imageView)

            itemView.setOnClickListener {
                onImageClickListener?.onImageClick()
            }
        }
    }

    interface OnImageClickListener {
        fun onImageClick()
    }
}
