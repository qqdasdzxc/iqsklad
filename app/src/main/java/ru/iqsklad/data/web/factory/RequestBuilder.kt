package ru.iqsklad.data.web.factory

import okhttp3.RequestBody
import org.json.JSONObject
import ru.iqsklad.data.web.request.BaseRequest

class RequestBuilder {

    private lateinit var request: BaseRequest

    fun createRequest(): RequestBuilder {
        request = BaseRequest()
        return this
    }

    fun setMethod(method: String): RequestBuilder {
        request.method = method
        return this
    }

    fun setParams(params: Map<String, Any>): RequestBuilder {
        request.params = BaseRequest.Params(params)
        return this
    }

    fun build(): RequestBody = with(request) {

        fun buildParams(): String? = if (params == null) params else JSONObject(params!!.params).toString()

        return RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(
                mapOf(
                    Pair("jsonrpc", jsonrpc),
                    Pair("method", method),
                    Pair("params", buildParams()),
                    Pair("id", id)
                )
            ).toString()
                .replace("\\\"","\"")
                .replaceFirst("\"{", "{")
                .replaceFirst("}\",", "},")
        )
    }
}