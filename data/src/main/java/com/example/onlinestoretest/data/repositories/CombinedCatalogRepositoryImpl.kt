package com.example.onlinestoretest.data.repositories

import com.example.onlinestoretest.data.retrofit.WebDataSource
import com.example.onlinestoretest.data.room.LocalDataSource
import com.example.onlinestoretest.domain.models.Product
import com.example.onlinestoretest.domain.repository.CatalogRepository

class CombinedCatalogRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val webDataSource: WebDataSource
) : CatalogRepository {

    override suspend fun getProducts(): List<Product> {
        val products = webDataSource.getProducts()
        val favoriteEntities = localDataSource.getFavorites()
        val favoriteIds = favoriteEntities.map { it.productId }

        for (product in products) {
            val isFavorite = favoriteIds.contains(product.id)
            product.isFavorite = isFavorite
        }

        return products
    }

    override suspend fun getProduct(productId: String): Product? {
        val product = webDataSource.getProduct(productId)

        product?.let {
            if (localDataSource.isFavorite(productId)) {
                product.isFavorite = true
            }
        }

        return product
    }

    override suspend fun toggleFavorite(productId: String) {
        localDataSource.toggleFavorite(productId)
    }

    override suspend fun getFavoritesCount(): Int {
        return localDataSource.getFavoritesCount()
    }

    override suspend fun getFavorites(): List<Product> {
        val favorites = localDataSource.getFavorites()
        val products = mutableListOf<Product>()

        for (favoriteEntity in favorites) {
            val product = webDataSource.getProduct(favoriteEntity.productId)
            product?.let {
                it.isFavorite = true
                products.add(it)
            }
        }

        return products
    }
}