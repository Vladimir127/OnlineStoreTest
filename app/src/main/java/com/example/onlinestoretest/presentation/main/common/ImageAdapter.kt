package com.example.onlinestoretest.presentation.main.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.ItemImageBinding
import com.squareup.picasso.Picasso

/**
 * Адаптер для ViewPager2. Используется в карточке товара в RecyclerView и на странице товара
 */
class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var onImageClickListener: (() -> Unit)? = null
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
        private val binding = ItemImageBinding.bind(itemView)

        fun bind(position: Int) {
            val imageResourceId = images[position]
            Picasso.get().load(imageResourceId).into(binding.imageView)

            itemView.setOnClickListener {
                onImageClickListener?.invoke()
            }
        }
    }
}
