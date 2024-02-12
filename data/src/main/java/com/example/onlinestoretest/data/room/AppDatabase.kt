package com.example.onlinestoretest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.onlinestoretest.data.room.entities.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}