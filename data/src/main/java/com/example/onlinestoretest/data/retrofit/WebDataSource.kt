package com.example.onlinestoretest.data.retrofit

import com.example.onlinestoretest.domain.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WebDataSource(private val productService: ProductService) {
    suspend fun getProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            try {
                // Чтобы обратиться к серверу без конкретного эндпоинта,
                // необходимо передать пустую строку в качестве URL
                productService.getProducts("").items
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getProduct(id: String): Product? {
        val products = getProducts()
        return products.find { it.id == id }
    }
}