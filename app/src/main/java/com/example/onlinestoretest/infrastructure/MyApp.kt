package com.example.onlinestoretest.infrastructure

import android.app.Application
import com.example.onlinestoretest.data.repositories.CatalogRepository
import com.example.onlinestoretest.data.repositories.WebCatalogRepositoryImpl
import com.example.onlinestoretest.data.retrofit.ProductService
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

    private val productService: ProductService by lazy { retrofit.create(ProductService::class.java)}

    val repository: CatalogRepository by lazy {
        WebCatalogRepositoryImpl(productService)
    }
}