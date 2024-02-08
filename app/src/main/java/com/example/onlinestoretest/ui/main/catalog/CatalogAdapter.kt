package com.example.onlinestoretest.ui.main.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.ItemProductBinding
import com.example.onlinestoretest.domain.Product

class CatalogAdapter : RecyclerView.Adapter<CatalogAdapter.ProductViewHolder>() {
    private var products: List<Product> = emptyList()

    fun setData(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product, parent, false
            )
        )
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
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
        }
    }
}