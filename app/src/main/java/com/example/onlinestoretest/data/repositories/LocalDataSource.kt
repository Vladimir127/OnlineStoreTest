package com.example.onlinestoretest.data.repositories

import com.example.onlinestoretest.data.room.FavoriteDao
import com.example.onlinestoretest.domain.entities.FavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource(private val favoriteDao: FavoriteDao) {
    suspend fun toggleFavorite(productId: String) {
        withContext(Dispatchers.IO) {
            val favoriteCount = favoriteDao.isFavorite(productId)
            if (favoriteCount > 0) {
                favoriteDao.deleteFavorite(productId)
            } else {
                val entity = FavoriteEntity(productId)
                favoriteDao.insertFavorite(entity)
            }
        }
    }

    suspend fun isFavorite(productId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val favoriteCount = favoriteDao.isFavorite(productId)
            favoriteCount > 0
        }
    }

    suspend fun getFavoritesCount(): Int {
        return withContext(Dispatchers.IO) {
            favoriteDao.getCount()
        }
    }

    suspend fun getFavorites(): List<FavoriteEntity> {
        return withContext(Dispatchers.IO) {
            favoriteDao.getAll()
        }
    }
}