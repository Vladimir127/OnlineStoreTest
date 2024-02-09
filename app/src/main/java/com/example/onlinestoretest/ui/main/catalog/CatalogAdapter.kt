package com.example.onlinestoretest.ui.main.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.ItemProductBinding
import com.example.onlinestoretest.domain.Product

class CatalogAdapter : RecyclerView.Adapter<CatalogAdapter.ProductViewHolder>() {
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
        holder.bind(currentProducts[position])
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)

        private lateinit var product: Product

        /*init {
            binding.root.setOnClickListener {
                listener.invoke(recipe.uri)
            }
        }*/

        fun bind(product: Product) {
            this.product = product

            binding.titleTextView.text = product.title
            binding.subtitleTextView.text = product.subtitle
            binding.oldPriceTextView.text = "${product.price.price} ${product.price.unit}"
            binding.priceTextView.text = "${product.price.priceWithDiscount} ${product.price.unit}"
            binding.discountTextView.text = "-${product.price.discount}%"

            if (product.feedback == null) {
                binding.zvImageView.visibility = View.INVISIBLE
                binding.ratingTextView.visibility = View.INVISIBLE
                binding.countTextView.visibility = View.INVISIBLE
            } else {
                binding.zvImageView.visibility = View.VISIBLE
                binding.ratingTextView.visibility = View.VISIBLE
                binding.countTextView.visibility = View.VISIBLE

                binding.ratingTextView.text = product.feedback.rating.toString()
                binding.countTextView.text = "(${product.feedback.count})"
            }
        }
    }
}