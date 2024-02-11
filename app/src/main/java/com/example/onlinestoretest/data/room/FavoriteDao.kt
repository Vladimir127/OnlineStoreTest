package com.example.onlinestoretest.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlinestoretest.domain.entities.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): List<FavoriteEntity>

    @Query("SELECT COUNT(*) FROM favorites")
    fun getCount(): Int

    @Query("SELECT COUNT(*) FROM favorites WHERE product_id = :productId")
    fun isFavorite(productId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(entity: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE product_id = :productId")
    fun deleteFavorite(productId: String)
}