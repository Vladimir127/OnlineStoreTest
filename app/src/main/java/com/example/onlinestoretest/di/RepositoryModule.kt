package com.example.onlinestoretest.di

import com.example.onlinestoretest.domain.repository.CatalogRepository
import com.example.onlinestoretest.data.repositories.CombinedCatalogRepositoryImpl
import com.example.onlinestoretest.data.room.LocalDataSource
import com.example.onlinestoretest.data.retrofit.WebDataSource
import dagger.Module
import dagger.Provides

@Module(includes = [DbModule::class, NetworkModule::class])
class RepositoryModule {
    @Provides
    fun provideCatalogRepository(localDataSource: com.example.onlinestoretest.data.room.LocalDataSource, webDataSource: com.example.onlinestoretest.data.retrofit.WebDataSource): com.example.onlinestoretest.domain.repository.CatalogRepository {
        return com.example.onlinestoretest.data.repositories.CombinedCatalogRepositoryImpl(
            localDataSource,
            webDataSource
        )
    }
}