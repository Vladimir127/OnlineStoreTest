package com.example.onlinestoretest.data.repositories

import com.example.onlinestoretest.domain.UserData


interface UserRepository {
    fun saveUserData(firstName: String, lastName: String, phoneNumber: String)
    fun getFirstName(): String
    fun getLastName(): String
    fun getPhoneNumber(): String

    fun getUserData(): UserData

    fun isUserDataFilled(): Boolean

    fun deleteUserData()
}
