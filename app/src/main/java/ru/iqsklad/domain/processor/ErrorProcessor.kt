package ru.iqsklad.domain.processor

import ru.dtk.lib.call.DtkApiException
import ru.iqsklad.data.web.response.api.ErrorResponse
import ru.servetta.customer.domain.processor.TypeCode

object ErrorProcessor {

    fun process(exception: Exception): ErrorResponse.Error {
        return when (exception) {
            is DtkApiException -> {
                checkErrorResponse(exception.data)
            }
            else -> {
                exception.printStackTrace()
                ErrorResponse.Error(typeCode = TypeCode.ERROR_NETWORK_CONNECTED)
            }
        }
    }

    private fun checkErrorResponse(response: Any?): ErrorResponse.Error {
        return when (response) {
            is ErrorResponse -> {
                val error = response.error
                checkErrorCode(error)
                error
            }
            else -> {
                ErrorResponse.Error(typeCode = TypeCode.ERROR_NETWORK_CONNECTED)
            }
        }
    }

    private fun checkErrorCode(error: ErrorResponse.Error?) {
        if (error == null) {
            return
        }

        when (error.typeCode) {
            else -> error.message
        }
    }
}