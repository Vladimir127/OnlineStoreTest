package com.example.onlinestoretest.domain.di

import com.example.onlinestoretest.ui.login.LoginViewModel
import com.example.onlinestoretest.ui.main.catalog.CatalogViewModel
import com.example.onlinestoretest.ui.main.favorites.FavoritesViewModel
import com.example.onlinestoretest.ui.main.product.ProductViewModel
import com.example.onlinestoretest.ui.main.profile.ProfileViewModel
import dagger.Component

@Component(modules = [NetworkModule::class, DbModule::class, RepositoryModule::class, UserModule::class])
interface AppComponent {
    fun inject(catalogViewModel: CatalogViewModel)
    fun inject(profileViewModel: ProfileViewModel)
    fun inject(loginViewModel: LoginViewModel)
    fun inject(favoritesViewModel: FavoritesViewModel)
    fun inject(productViewModel: ProductViewModel)
}