package com.example.onlinestoretest.ui.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlinestoretest.domain.UserData
import com.example.onlinestoretest.infrastructure.MyApp

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository by lazy { (application as MyApp).userRepository }

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData>
        get() = _userData

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    fun loadData() {
        val data = userRepository.getUserData()
        _userData.value = data
    }

    fun logout() {
        userRepository.deleteUserData()
        _navigateToLogin.value = true
    }
}