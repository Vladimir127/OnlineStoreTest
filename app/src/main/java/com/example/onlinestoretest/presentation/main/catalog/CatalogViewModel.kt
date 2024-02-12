package com.example.onlinestoretest.presentation.main.catalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestoretest.domain.repository.CatalogRepository
import com.example.onlinestoretest.domain.models.Product
import com.example.onlinestoretest.infrastructure.MyApp
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel (application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var catalogRepository: com.example.onlinestoretest.domain.repository.CatalogRepository

    init {
        (application as MyApp).appComponent.inject(this)
    }

    private val _products: MutableLiveData<List<com.example.onlinestoretest.domain.models.Product>> = MutableLiveData()
    val products: LiveData<List<com.example.onlinestoretest.domain.models.Product>>
        get() = _products

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() = _error

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = catalogRepository.getProducts()
                _products.value = products
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            catalogRepository.toggleFavorite(productId)
        }
    }
}