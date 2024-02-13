package com.example.onlinestoretest.infrastructure

import android.app.Application
import com.example.onlinestoretest.di.AppComponent
import com.example.onlinestoretest.di.DaggerAppComponent
import com.example.onlinestoretest.di.DbModule
import com.example.onlinestoretest.di.NetworkModule
import com.example.onlinestoretest.di.RepositoryModule
import com.example.onlinestoretest.di.UserModule

class MyApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .dbModule(DbModule(this))
            .repositoryModule(RepositoryModule())
            .userModule(UserModule())
            .build()
    }
}
