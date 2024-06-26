package com.example.onlinestoretest.presentation.main.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.ItemProductBinding
import com.example.onlinestoretest.domain.models.Product
import com.example.onlinestoretest.utils.ImageMapUtil.Companion.imageMap
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Адаптер для списка товаров. Используется в разделах "Каталог" и "Избранное"
 */
class ProductAdapter(val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    var onItemClickListener: ((String) -> Unit)? = null
    var favoriteItemClickListener: FavoriteItemClickListener? = null

    private var originalProducts: List<Product> = emptyList()
    private var currentProducts: List<Product> = emptyList()

    private var sortOption = context.resources.getString(R.string.sort_popularity)

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
        sortBy(this.sortOption)
        notifyDataSetChanged()
    }

    fun sortBy(sortOption: String) {
        this.sortOption = sortOption
        currentProducts = when (sortOption) {
            context.resources.getString(R.string.sort_popularity) -> currentProducts.sortedByDescending { it.feedback.rating }
            context.resources.getString(R.string.sort_price_descending) -> currentProducts.sortedByDescending { it.price.priceWithDiscount.toDouble() }
            context.resources.getString(R.string.sort_price_ascending) -> currentProducts.sortedBy { it.price.priceWithDiscount.toDouble() }
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
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)

        private lateinit var product: Product
        private val imageAdapter = ImageAdapter()

        init {
            binding.imageViewPager.adapter = imageAdapter

            setOnClickListeners()
        }

        fun bind(product: Product) {
            this.product = product

            initViewPager()
            initFavoriteButton()


            binding.titleTextView.text = product.title
            binding.subtitleTextView.text = product.subtitle
            binding.oldPriceTextView.text = context.resources.getString(
                R.string.price_with_unit,
                product.price.price,
                product.price.unit
            )
            binding.priceTextView.text = context.resources.getString(
                R.string.price_with_unit,
                product.price.priceWithDiscount,
                product.price.unit
            )
            binding.discountTextView.text =
                context.resources.getString(R.string.discount, product.price.discount)

            if (product.feedback == null) {
                binding.zvImageView.visibility = View.INVISIBLE
                binding.ratingTextView.visibility = View.INVISIBLE
                binding.countTextView.visibility = View.INVISIBLE
            } else {
                binding.zvImageView.visibility = View.VISIBLE
                binding.ratingTextView.visibility = View.VISIBLE
                binding.countTextView.visibility = View.VISIBLE

                binding.ratingTextView.text = product.feedback.rating.toString()
                binding.countTextView.text =
                    context.resources.getString(R.string.feedback_count, product.feedback.count)
            }
        }

        private fun initViewPager() {
            imageAdapter.setData(imageMap[product.id] ?: emptyList())
            TabLayoutMediator(
                binding.tabLayout,
                binding.imageViewPager
            ) { tab, position -> }.attach()
        }

        private fun initFavoriteButton() {
            setFavoriteButtonIcon()

            binding.addToFavoriteButton.setOnClickListener {
                favoriteItemClickListener?.onToggleFavorite(product.id)
                product.isFavorite = !product.isFavorite
                setFavoriteButtonIcon()
            }
        }

        private fun setFavoriteButtonIcon() {
            if (product.isFavorite) {
                binding.addToFavoriteButton.setImageResource(R.drawable.ic_heart_filled)
            } else {
                binding.addToFavoriteButton.setImageResource(R.drawable.ic_heart_stroke)
            }
        }

        private fun setOnClickListeners() {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(product.id)
            }

            imageAdapter.onImageClickListener = {
                onItemClickListener?.invoke(product.id)
            }
        }
    }

    interface FavoriteItemClickListener {
        fun onToggleFavorite(productId: String)
    }
}