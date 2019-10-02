package ru.iqsklad.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.iqsklad.data.Constants

object NetworkUtils {

    fun getGsonConverter(): Gson {
        return GsonBuilder()
                .setDateFormat(Constants.DEFAULT_DATE_FORMAT)
                .create()
    }

    fun <T> listToJsonString(list: List<T>): String {
        return getGsonConverter()
                .toJsonTree(list)
                .toString()
    }
}