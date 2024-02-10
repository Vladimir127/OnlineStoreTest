package com.example.onlinestoretest.ui.main.catalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.ItemProductBinding
import com.example.onlinestoretest.domain.Product
import com.example.onlinestoretest.ui.main.common.ImageAdapter
import com.example.onlinestoretest.utils.ImageMapUtil.Companion.imageMap
import com.google.android.material.tabs.TabLayoutMediator

class CatalogAdapter(val context: Context) : RecyclerView.Adapter<CatalogAdapter.ProductViewHolder>() {
    private var originalProducts: List<Product> = emptyList()
    private var currentProducts: List<Product> = emptyList()

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
        val product = currentProducts[position]

        holder.bind(product)

        holder.itemView.setOnClickListener {
            val action = CatalogFragmentDirections.actionCatalogFragmentToProductFragment(product)
            it.findNavController().navigate(action)
        }
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)

        private lateinit var product: Product
        private val imageAdapter = ImageAdapter()

        init {
            binding.imageViewPager.adapter = imageAdapter

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
            TabLayoutMediator(binding.tabLayout, binding.imageViewPager) { tab, position -> }.attach()
        }
    }
}