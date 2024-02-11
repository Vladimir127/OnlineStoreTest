package com.example.onlinestoretest.infrastructure

import android.app.Application
import com.example.onlinestoretest.domain.di.AppComponent
import com.example.onlinestoretest.domain.di.DaggerAppComponent
import com.example.onlinestoretest.domain.di.DbModule
import com.example.onlinestoretest.domain.di.NetworkModule
import com.example.onlinestoretest.domain.di.RepositoryModule
import com.example.onlinestoretest.domain.di.UserModule

class MyApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .dbModule(DbModule(this))
            .repositoryModule(RepositoryModule())
            .userModule(UserModule())
            .build()
    }

    /*private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val productService: ProductService by lazy { retrofit.create(ProductService::class.java) }

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

    private val webDataSource by lazy { WebDataSource(productService) }

    val catalogRepository: CatalogRepository by lazy {
        CombinedCatalogRepositoryImpl(localDataSource, webDataSource)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(sharedPreferences)
    }*/
}
