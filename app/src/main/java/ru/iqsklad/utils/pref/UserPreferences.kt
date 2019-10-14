package ru.iqsklad.utils.pref

import android.content.Context
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.utils.ConverterUtils

object UserPreferences {
    private const val USER_PREFERENCES = "USER_PREFERENCES"
    private const val USER_KEY = "USER_KEY"

    fun getUser(context: Context): User? {
        val preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
        return ConverterUtils.objFromJsonString(preferences.getString(USER_KEY, null))
    }

    fun saveUser(context: Context, user: User) {
        val preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
        preferences.edit().putString(USER_KEY, ConverterUtils.objToJsonString(user)).apply()
    }

    fun removeUser(context: Context) {
        val preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
        preferences.edit().putString(USER_KEY, null).apply()
    }
}