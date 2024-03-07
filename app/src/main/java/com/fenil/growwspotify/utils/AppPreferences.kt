package com.fenil.growwspotify.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import javax.inject.Inject

class AppPreferences @Inject constructor(val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    private val LAST_SAVED_TOKEN_TIME_KEY = "last_token_time"
    private val AUTH_TOKEN = "auth_token"
    private val EXPIRE_TIME = "expire_time"

    fun saveAuthTokenWithCurrentTime(authToken: String, expireTime: Int) {
        val currentTime = Calendar.getInstance().timeInMillis
        sharedPreferences.edit().putLong(LAST_SAVED_TOKEN_TIME_KEY, currentTime).apply()
        sharedPreferences.edit().putString(AUTH_TOKEN, authToken).apply()
        sharedPreferences.edit().putInt(EXPIRE_TIME, expireTime).apply()
    }

    fun hasTokenExpired(): Boolean {
        val currentTime = Calendar.getInstance().timeInMillis
        val lastSavedTokenTime = sharedPreferences.getLong(LAST_SAVED_TOKEN_TIME_KEY, 0)
        val oneHourInMillis = sharedPreferences.getLong(EXPIRE_TIME, 60 * 60 * 1000 ) // 1 hour in milliseconds default

        return currentTime - lastSavedTokenTime >= oneHourInMillis
    }

    fun getAuthToken(): String {
        return sharedPreferences.getString(AUTH_TOKEN, "") ?: ""
    }
}
