package com.example.onlinestoretest.data.repositories

import android.content.SharedPreferences
import com.example.onlinestoretest.domain.UserData

class UserRepositoryImpl(private val sharedPreferences: SharedPreferences) : UserRepository {
    private companion object {
        const val PREF_NAME = "user_prefs"
        const val KEY_FIRST_NAME = "first_name"
        const val KEY_LAST_NAME = "last_name"
        const val KEY_PHONE_NUMBER = "phone_number"
    }

    override fun saveUserData(firstName: String, lastName: String, phoneNumber: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_FIRST_NAME, firstName)
        editor.putString(KEY_LAST_NAME, lastName)
        editor.putString(KEY_PHONE_NUMBER, phoneNumber)
        editor.apply()
    }

    override fun getFirstName(): String {
        return sharedPreferences.getString(KEY_FIRST_NAME, "") ?: ""
    }

    override fun getLastName(): String {
        return sharedPreferences.getString(KEY_LAST_NAME, "") ?: ""
    }

    override fun getPhoneNumber(): String {
        return sharedPreferences.getString(KEY_PHONE_NUMBER, "") ?: ""
    }

    override fun getUserData(): UserData {
        val firstName = sharedPreferences.getString(KEY_FIRST_NAME, "") ?: ""
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, "") ?: ""
        val phoneNumber = sharedPreferences.getString(KEY_PHONE_NUMBER, "") ?: ""

        return UserData(firstName, lastName, phoneNumber)
    }

    override fun isUserDataFilled(): Boolean {
        val firstName = sharedPreferences.getString(KEY_FIRST_NAME, "")
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, "")
        val phoneNumber = sharedPreferences.getString(KEY_PHONE_NUMBER, "")

        return !(firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || phoneNumber.isNullOrEmpty())
    }

    override fun deleteUserData() {
        sharedPreferences.edit().apply {
            remove(KEY_FIRST_NAME)
            remove(KEY_LAST_NAME)
            remove(KEY_PHONE_NUMBER)
        }.apply()
    }
}
