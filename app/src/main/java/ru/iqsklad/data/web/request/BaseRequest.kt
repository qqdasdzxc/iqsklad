package ru.iqsklad.data.web.request

import okhttp3.RequestBody
import org.json.JSONObject

open class BaseRequest(
    private val jsonrpc: String = "2.0",
    val method: String? = null,
    val id: String = "1"
) {

    fun createJsonRequestBody(): RequestBody =
        RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(
                mapOf(
                    Pair("jsonrpc", jsonrpc),
                    Pair("method", method),
                    Pair("id", id)
                )
            ).toString()
        )
}