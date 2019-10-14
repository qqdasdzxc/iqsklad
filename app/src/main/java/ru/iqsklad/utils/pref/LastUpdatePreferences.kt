package ru.iqsklad.utils.pref

import android.content.Context

object LastUpdatePreferences {

    private const val LAST_UPDATE_PREFERENCES = "LAST_UPDATE_PREFERENCES"
    private const val LAST_UPDATE_KEY = "LAST_UPDATE_KEY"
    const val DEFAULT_LAST_UPDATE_TIME = 0L

    fun getTime(context: Context): Long {
        val preferences = context.getSharedPreferences(LAST_UPDATE_PREFERENCES, Context.MODE_PRIVATE)
        return preferences.getLong(LAST_UPDATE_KEY, DEFAULT_LAST_UPDATE_TIME)
    }

    fun saveTime(context: Context, lastUpdateTimeInMillis: Long) {
        val preferences = context.getSharedPreferences(LAST_UPDATE_PREFERENCES, Context.MODE_PRIVATE)
        preferences.edit().putLong(LAST_UPDATE_KEY, lastUpdateTimeInMillis).apply()
    }
}