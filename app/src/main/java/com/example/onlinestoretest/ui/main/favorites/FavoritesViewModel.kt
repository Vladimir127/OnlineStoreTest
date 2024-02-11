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