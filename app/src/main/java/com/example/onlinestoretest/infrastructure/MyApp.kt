package com.example.onlinestoretest.infrastructure

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.onlinestoretest.data.repositories.CatalogRepository
import com.example.onlinestoretest.data.repositories.CombinedCatalogRepositoryImpl
import com.example.onlinestoretest.data.repositories.LocalDataSource
import com.example.onlinestoretest.data.repositories.UserRepository
import com.example.onlinestoretest.data.repositories.UserRepositoryImpl
import com.example.onlinestoretest.data.repositories.WebDataSource
import com.example.onlinestoretest.data.retrofit.ProductService
import com.example.onlinestoretest.data.room.AppDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://run.mocky.io/v3/97e721a7-0a66-4cae-b445-83cc0bcf9010/"

class MyApp : Application() {
    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "favorites-db"
        ).build()
    }

    private val favoriteDao by lazy {
        database.favoriteDao()
    }

    private val localDataSource by lazy {
        LocalDataSource(favoriteDao)
    }

    private val productService: ProductService by lazy { retrofit.create(ProductService::class.java) }

    private val webDataSource by lazy { WebDataSource(productService) }

    val catalogRepository: CatalogRepository by lazy {
        CombinedCatalogRepositoryImpl(localDataSource, webDataSource)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(sharedPreferences)
    }
}