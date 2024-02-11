package com.example.onlinestoretest.ui.main.catalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestoretest.domain.Product
import com.example.onlinestoretest.infrastructure.MyApp
import kotlinx.coroutines.launch

class CatalogViewModel(application: Application) : AndroidViewModel(application) {
    private val catalogRepository by lazy { (application as MyApp).catalogRepository }

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>>
        get() = _products

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = catalogRepository.getProducts()
                _products.value = products
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            catalogRepository.toggleFavorite(productId)
        }
    }
}