package com.example.onlinestoretest.ui.main.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestoretest.domain.Product
import com.example.onlinestoretest.infrastructure.MyApp
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val catalogRepository by lazy { (application as MyApp).catalogRepository }

    private val _product: MutableLiveData<Product?> = MutableLiveData()
    val product: LiveData<Product?>
        get() = _product

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            val product = catalogRepository.getProduct(productId)
            _product.value = product
        }
    }
}