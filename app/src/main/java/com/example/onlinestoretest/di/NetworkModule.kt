package com.example.onlinestoretest.di

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
    fun provideProductService(retrofit: Retrofit): com.example.onlinestoretest.data.retrofit.ProductService {
        return retrofit.create(com.example.onlinestoretest.data.retrofit.ProductService::class.java)
    }

    @Provides
    fun provideWebDataSource(productService: com.example.onlinestoretest.data.retrofit.ProductService): com.example.onlinestoretest.data.retrofit.WebDataSource {
        return com.example.onlinestoretest.data.retrofit.WebDataSource(productService)
    }
}