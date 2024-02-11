package com.example.onlinestoretest.ui.main.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestoretest.domain.Product
import com.example.onlinestoretest.infrastructure.MyApp
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application): AndroidViewModel(application) {
    private val catalogRepository by lazy { (application as MyApp).catalogRepository }

    private val _favoriteProducts: MutableLiveData<List<Product>> = MutableLiveData()

    val favoriteProducts: LiveData<List<Product>>
        get() = _favoriteProducts

    fun loadFavorites(){
        viewModelScope.launch {
            val products = catalogRepository.getFavorites()
            _favoriteProducts.value = products
        }
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            catalogRepository.toggleFavorite(productId)
            _favoriteProducts.value = catalogRepository.getFavorites()
        }
    }
}