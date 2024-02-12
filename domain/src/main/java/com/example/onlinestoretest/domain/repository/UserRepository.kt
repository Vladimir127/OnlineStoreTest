package com.example.onlinestoretest.domain.repository

import com.example.onlinestoretest.domain.models.UserData


interface UserRepository {
    fun saveUserData(firstName: String, lastName: String, phoneNumber: String)
    fun getFirstName(): String
    fun getLastName(): String
    fun getPhoneNumber(): String

    fun getUserData(): UserData

    fun isUserDataFilled(): Boolean

    fun deleteUserData()
}
