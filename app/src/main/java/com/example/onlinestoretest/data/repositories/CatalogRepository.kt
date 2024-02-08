package com.example.onlinestoretest.data.repositories

import com.example.onlinestoretest.domain.Product

interface CatalogRepository {
    suspend fun getProducts(): List<Product>
}