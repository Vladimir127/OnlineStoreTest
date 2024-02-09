package com.example.onlinestoretest.ui.main.catalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.ItemProductBinding
import com.example.onlinestoretest.databinding.ItemProductLinearBinding
import com.example.onlinestoretest.domain.Product
import com.google.android.material.tabs.TabLayoutMediator

class CatalogAdapter(val context: Context) : RecyclerView.Adapter<CatalogAdapter.ProductViewHolder>() {
    private var originalProducts: List<Product> = emptyList()
    private var currentProducts: List<Product> = emptyList()

    val imageMap: Map<String, List<Int>> = mapOf(
        "cbf0c984-7c6c-4ada-82da-e29dc698bb50" to listOf(R.drawable.image_vox, R.drawable.image_eveline),
        "54a876a5-2205-48ba-9498-cfecff4baa6e" to listOf(R.drawable.image_deep_clean, R.drawable.image_coenzyme),
        "75c84407-52e1-4cce-a73a-ff2d3ac031b3" to listOf(R.drawable.image_eveline, R.drawable.image_vox),
        "16f88865-ae74-4b7c-9d85-b68334bb97db" to listOf(R.drawable.image_deco, R.drawable.image_hand_mask),
        "26f88856-ae74-4b7c-9d85-b68334bb97db" to listOf(R.drawable.image_coenzyme, R.drawable.image_deco),
        "15f88865-ae74-4b7c-9d81-b78334bb97db" to listOf(R.drawable.image_vox, R.drawable.image_deep_clean),
        "88f88865-ae74-4b7c-9d81-b78334bb97db" to listOf(R.drawable.image_hand_mask, R.drawable.image_deco),
        "55f58865-ae74-4b7c-9d81-b78334bb97db" to listOf(R.drawable.image_deep_clean, R.drawable.image_eveline)
    )

    fun setData(products: List<Product>) {
        this.originalProducts = products
        this.currentProducts = products
        notifyDataSetChanged()
    }

    fun filterByTag(tag: String) {
        val filteredList =
            if (tag == "watch_all")
                originalProducts
            else
                originalProducts.filter { product ->
                    product.tags.contains(tag)
                }
        currentProducts = filteredList
        notifyDataSetChanged()
    }

    fun sortBy(sortOption: String) {
        currentProducts = when (sortOption) {
            "По популярности" -> currentProducts.sortedByDescending { it.feedback.rating }
            "По уменьшению цены" -> currentProducts.sortedByDescending { it.price.priceWithDiscount.toDouble() }
            "По возрастанию цены" -> currentProducts.sortedBy { it.price.priceWithDiscount.toDouble() }
            else -> currentProducts
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product, parent, false
            )
        )
    }

    override fun getItemCount() = currentProducts.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(currentProducts[position])
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)

        private lateinit var product: Product
        private val imageAdapter = ImageAdapter()

        init {
            binding.imageViewPager.adapter = imageAdapter
            TabLayoutMediator(binding.tabLayout, binding.imageViewPager) { tab, position ->
                // Установите текст или иконку для каждой вкладки (индикатора)
                // Например, tab.text = "Изображение ${position + 1}"
            }.attach()
        }

        fun bind(product: Product) {
            this.product = product

            initViewPager()

            binding.titleTextView.text = product.title
            binding.subtitleTextView.text = product.subtitle
            binding.oldPriceTextView.text = context.resources.getString(R.string.price_with_unit, product.price.price, product.price.unit)
            binding.priceTextView.text = context.resources.getString(R.string.price_with_unit, product.price.priceWithDiscount, product.price.unit)
            binding.discountTextView.text = context.resources.getString(R.string.discount, product.price.discount)

            if (product.feedback == null) {
                binding.zvImageView.visibility = View.INVISIBLE
                binding.ratingTextView.visibility = View.INVISIBLE
                binding.countTextView.visibility = View.INVISIBLE
            } else {
                binding.zvImageView.visibility = View.VISIBLE
                binding.ratingTextView.visibility = View.VISIBLE
                binding.countTextView.visibility = View.VISIBLE

                binding.ratingTextView.text = product.feedback.rating.toString()
                binding.countTextView.text = context.resources.getString(R.string.feedback_count, product.feedback.count)
            }
        }

        private fun initViewPager() {
            imageAdapter.setData(imageMap[product.id] ?: emptyList())
        }
    }
}