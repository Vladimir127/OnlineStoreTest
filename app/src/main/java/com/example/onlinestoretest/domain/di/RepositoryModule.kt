package com.example.onlinestoretest.domain.di

import com.example.onlinestoretest.data.repositories.CatalogRepository
import com.example.onlinestoretest.data.repositories.CombinedCatalogRepositoryImpl
import com.example.onlinestoretest.data.repositories.LocalDataSource
import com.example.onlinestoretest.data.repositories.WebDataSource
import dagger.Module
import dagger.Provides

@Module(includes = [DbModule::class, NetworkModule::class])
class RepositoryModule {
    @Provides
    fun provideCatalogRepository(localDataSource: LocalDataSource, webDataSource: WebDataSource): CatalogRepository {
        return CombinedCatalogRepositoryImpl(localDataSource, webDataSource)
    }
}