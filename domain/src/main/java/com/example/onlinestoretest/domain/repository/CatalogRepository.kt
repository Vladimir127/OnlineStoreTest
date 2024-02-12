package com.example.onlinestoretest.domain.repository

import com.example.onlinestoretest.domain.models.Product

interface CatalogRepository {
    suspend fun getProducts(): List<Product>

    suspend fun getProduct(productId: String): Product?

    suspend fun toggleFavorite(productId: String)

    suspend fun getFavoritesCount(): Int

    suspend fun getFavorites(): List<Product>
}