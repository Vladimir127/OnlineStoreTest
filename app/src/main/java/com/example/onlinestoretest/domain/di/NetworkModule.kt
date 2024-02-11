package com.example.onlinestoretest.domain.di

import com.example.onlinestoretest.data.repositories.WebDataSource
import com.example.onlinestoretest.data.retrofit.ProductService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://run.mocky.io/v3/97e721a7-0a66-4cae-b445-83cc0bcf9010/"

@Module
class NetworkModule {
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    fun provideWebDataSource(productService: ProductService): WebDataSource {
        return WebDataSource(productService)
    }
}