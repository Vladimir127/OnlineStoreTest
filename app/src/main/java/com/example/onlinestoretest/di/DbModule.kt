package com.example.onlinestoretest.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
class DbModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideDatabase(context: Context): com.example.onlinestoretest.data.room.AppDatabase {
        return Room.databaseBuilder(
            context,
            com.example.onlinestoretest.data.room.AppDatabase::class.java, "favorites-db"
        ).build()
    }

    @Provides
    fun provideDao(database: com.example.onlinestoretest.data.room.AppDatabase): com.example.onlinestoretest.data.room.FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    fun provideLocalDataSource(favoriteDao: com.example.onlinestoretest.data.room.FavoriteDao): com.example.onlinestoretest.data.room.LocalDataSource {
        return com.example.onlinestoretest.data.room.LocalDataSource(favoriteDao)
    }
}