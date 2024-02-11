package com.example.onlinestoretest.domain.di

import android.content.Context
import androidx.room.Room
import com.example.onlinestoretest.data.repositories.LocalDataSource
import com.example.onlinestoretest.data.room.AppDatabase
import com.example.onlinestoretest.data.room.FavoriteDao
import dagger.Module
import dagger.Provides

@Module
class DbModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "favorites-db"
        ).build()
    }

    @Provides
    fun provideDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    fun provideLocalDataSource(favoriteDao: FavoriteDao): LocalDataSource {
        return LocalDataSource(favoriteDao)
    }
}