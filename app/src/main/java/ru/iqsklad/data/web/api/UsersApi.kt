package ru.iqsklad.data.web.api

import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import ru.iqsklad.data.web.request.BaseRequest
import ru.iqsklad.data.web.response.UsersResponse

interface UsersApi {

    @POST("eqar")
    fun getUsersAsync(
        @Body body: RequestBody = BaseRequest(method = "person.getList").createJsonRequestBody()
    ): Deferred<UsersResponse>

    @POST("eqar")
    fun getInvoicesAsync(
        @Body body: RequestBody = BaseRequest(
            method = "invoice.getList",
            params = BaseRequest.Params(
                params = mapOf(
                    Pair("date", "2019-10-02")
                )
            )
        ).createJsonRequestBody()
    ): Deferred<UsersResponse>
}