package ru.iqsklad.data.web.response.api

import com.google.gson.annotations.SerializedName
import ru.iqsklad.domain.processor.TypeCode

data class ErrorResponse(
    @SerializedName("error")
    var error: Error
) {
    data class Error(
        @SerializedName("message")
        val message: String? = null,
        @SerializedName("code")
        val typeCode: TypeCode? = null
    )
}