package com.example.onlinestoretest.di

import com.example.onlinestoretest.data.retrofit.WebDataSource
import com.example.onlinestoretest.data.room.LocalDataSource
import com.example.onlinestoretest.domain.repository.CatalogRepository
import dagger.Module
import dagger.Provides

@Module(includes = [DbModule::class, NetworkModule::class])
class RepositoryModule {
    @Provides
    fun provideCatalogRepository(localDataSource: LocalDataSource, webDataSource: WebDataSource): CatalogRepository {
        return com.example.onlinestoretest.data.repositories.CombinedCatalogRepositoryImpl(
            localDataSource,
            webDataSource
        )
    }
}