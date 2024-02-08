package com.example.onlinestoretest.data.repositories

import com.example.onlinestoretest.data.retrofit.ProductService
import com.example.onlinestoretest.domain.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WebCatalogRepositoryImpl(private val productService: ProductService): CatalogRepository {
    override suspend fun getProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            try {
                productService.getProducts("").items
            } catch (e: Exception) {
                throw e
            }
        }
    }
}