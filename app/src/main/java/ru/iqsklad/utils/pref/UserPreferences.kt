package ru.iqsklad.utils.pref

import android.content.Context

object UserPreferences {
    private const val USER_PREFERENCES = "USER_PREFERENCES"
    private const val USER_KEY = "USER_KEY"

    fun getUserId(context: Context): String? {
        val preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
        return preferences.getString(USER_KEY, null)
    }

    fun saveUserId(context: Context, userID: String) {
        val preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
        preferences.edit().putString(USER_KEY, userID).apply()
    }

    fun removeUserID(context: Context) {
        val preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
        preferences.edit().putString(USER_KEY, null).apply()
    }
}