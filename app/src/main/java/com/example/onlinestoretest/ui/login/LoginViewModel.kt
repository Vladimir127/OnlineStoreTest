package com.example.onlinestoretest.ui.login

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlinestoretest.infrastructure.MyApp

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigateToMain = MutableLiveData<Boolean>()
    val navigateToMain: LiveData<Boolean>
        get() = _navigateToMain

    private val _nameValid = MutableLiveData<Boolean>()
    val nameValid: LiveData<Boolean>
        get() = _nameValid

    private val _surnameValid = MutableLiveData<Boolean>()
    val surnameValid: LiveData<Boolean>
        get() = _surnameValid

    private val _phoneValid = MutableLiveData<Boolean>()
    val phoneValid: LiveData<Boolean>
        get() = _phoneValid

    private val _loginButtonEnabled = MutableLiveData<Boolean>().apply { value = false }
    val loginButtonEnabled: LiveData<Boolean>
        get() = _loginButtonEnabled

    private val userRepository by lazy { (application as MyApp).userRepository }

    fun validateName(name: String) {
        val regex = Regex("^[А-Яа-я]+$")
        _nameValid.value = name.matches(regex)
        checkFieldsValidity()
    }

    fun validateSurname(name: String) {
        val regex = Regex("^[А-Яа-я]+$")
        _surnameValid.value = name.matches(regex)
        checkFieldsValidity()
    }

    fun validatePhone(name: String) {
        val regex = Regex("^\\+7 \\d{3} \\d{3}-\\d{2}-\\d{2}\$")
        _phoneValid.value = name.matches(regex)
        checkFieldsValidity()
    }

    private fun checkFieldsValidity() {
        val isNameValid = _nameValid.value ?: false
        val isSurnameValid = _surnameValid.value ?: false
        val isPhoneValid = _phoneValid.value ?: false
        _loginButtonEnabled.value = isNameValid && isSurnameValid && isPhoneValid
    }

    fun onLoginButtonClick(name: String, surname: String, phone: String) {
        userRepository.saveUserData(name, surname, phone)
        _navigateToMain.value = true
    }

    fun checkUserData() {
        if (userRepository.isUserDataFilled()) {
            _navigateToMain.value = true
        }
    }
}