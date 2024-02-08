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
    private val _products: MutableLiveData<List<Product>> = MutableLiveData()

    private val catalogRepository by lazy { (application as MyApp).repository }

    val products: LiveData<List<Product>>
        get() = _products

    init {
        // Вместо фейковой коллекции данных здесь можно получить данные с сервера и установить их в _products
        /*val fakeProducts = listOf(
            Product("ESFOLIO", "Лосьон для тела `ESFOLIO` COENZYME Q10 Увлажняющий 500 мл"),
            Product("A`PIEU", "Пенка для умывания A`PIEU` `DEEP CLEAN` 200  мл"),
            Product(
                "DECO.",
                "Салфетки для лица `DECO.` матирующие с экстрактом зеленого чая 100 шт"
            ),
            Product("LP CARE", "Маска-перчатки для рук `LP-CARE` увлажняющая 40 мл")
        )
        _products.value = fakeProducts*/
    }

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = catalogRepository.getProducts()
                _products.value = products
            } catch (e: Exception) {
                val message = e.message
                e.printStackTrace()
            }
        }
    }
}