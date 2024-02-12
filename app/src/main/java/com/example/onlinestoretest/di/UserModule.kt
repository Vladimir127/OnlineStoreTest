package com.example.onlinestoretest.di

import android.content.Context
import android.content.SharedPreferences
import com.example.onlinestoretest.domain.repository.UserRepository
import com.example.onlinestoretest.data.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [DbModule::class])
class UserModule() {
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideUserRepository(sharedPreferences: SharedPreferences): com.example.onlinestoretest.domain.repository.UserRepository {
        return com.example.onlinestoretest.data.repositories.UserRepositoryImpl(sharedPreferences)
    }
}