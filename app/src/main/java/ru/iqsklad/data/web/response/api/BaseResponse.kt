package ru.iqsklad.data.web.response.api

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @SerializedName("result")
    var data: T? = null
)