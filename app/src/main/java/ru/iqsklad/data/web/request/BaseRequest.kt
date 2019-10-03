package ru.iqsklad.data.web.request

open class BaseRequest(
    var jsonrpc: String = "2.0",
    var method: String? = null,
    var params: Params? = null,
    var id: String = "1"
) {
    class Params(
        val params: Map<String, String>
    )
}