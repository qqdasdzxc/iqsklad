package ru.iqsklad.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.iqsklad.data.Constants

object ConverterUtils {

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

    fun <T> objToJsonString(model: T): String {
        return getGsonConverter()
            .toJson(model)
            .toString()
    }

    inline fun <reified T> objFromJsonString(modelString: String?): T {
        return getGsonConverter()
            .fromJson(modelString, T::class.java)
    }
}