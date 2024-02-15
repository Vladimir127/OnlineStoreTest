package com.example.onlinestoretest.di

import com.example.onlinestoretest.presentation.login.LoginViewModel
import com.example.onlinestoretest.presentation.main.catalog.CatalogViewModel
import com.example.onlinestoretest.presentation.main.favorites.FavoriteProductsViewModel
import com.example.onlinestoretest.presentation.main.product.ProductViewModel
import com.example.onlinestoretest.presentation.main.profile.ProfileViewModel
import dagger.Component

@Component(modules = [NetworkModule::class, DbModule::class, RepositoryModule::class, UserModule::class])
interface AppComponent {
    fun inject(catalogViewModel: CatalogViewModel)
    fun inject(profileViewModel: ProfileViewModel)
    fun inject(loginViewModel: LoginViewModel)
    fun inject(favoriteProductsViewModel: FavoriteProductsViewModel)
    fun inject(productViewModel: ProductViewModel)
}