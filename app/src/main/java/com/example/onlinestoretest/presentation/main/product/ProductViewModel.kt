package com.example.onlinestoretest.presentation.main.product

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

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var catalogRepository: com.example.onlinestoretest.domain.repository.CatalogRepository

    init {
        (application as MyApp).appComponent.inject(this)
    }

    private val _product: MutableLiveData<com.example.onlinestoretest.domain.models.Product?> = MutableLiveData()
    val product: LiveData<com.example.onlinestoretest.domain.models.Product?>
        get() = _product

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() = _error

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            try {
                val product = catalogRepository.getProduct(productId)
                _product.value = product
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }
}