package com.example.onlinestoretest.presentation.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlinestoretest.domain.models.UserData
import com.example.onlinestoretest.domain.repository.CatalogRepository
import com.example.onlinestoretest.domain.repository.UserRepository
import com.example.onlinestoretest.infrastructure.MyApp
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var catalogRepository: CatalogRepository

    init {
        (application as MyApp).appComponent.inject(this)
    }

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData>
        get() = _userData

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    private val _favoritesCount = MutableLiveData<Int>()
    val favoritesCount: LiveData<Int>
        get() = _favoritesCount

    fun loadData() {
        val data = userRepository.getUserData()
        _userData.value = data
    }

    fun loadFavoritesCount(){
        viewModelScope.launch {
            val count = catalogRepository.getFavoritesCount()
            _favoritesCount.value = count
        }
    }

    fun logout() {
        userRepository.deleteUserData()
        _navigateToLogin.value = true
    }
}