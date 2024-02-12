package com.example.onlinestoretest.ui.main.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestoretest.data.repositories.CatalogRepository
import com.example.onlinestoretest.domain.Product
import com.example.onlinestoretest.infrastructure.MyApp
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel(application: Application): AndroidViewModel(application) {
    @Inject
    lateinit var catalogRepository: CatalogRepository

    init {
        (application as MyApp).appComponent.inject(this)
    }

    private val _favoriteProducts: MutableLiveData<List<Product>> = MutableLiveData()

    val favoriteProducts: LiveData<List<Product>>
        get() = _favoriteProducts

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() = _error

    fun loadFavorites(){
        viewModelScope.launch {
            try {
                val products = catalogRepository.getFavorites()
                _favoriteProducts.value = products
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            catalogRepository.toggleFavorite(productId)
            _favoriteProducts.value = catalogRepository.getFavorites()
        }
    }
}