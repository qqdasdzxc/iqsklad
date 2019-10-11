package ru.iqsklad.data.web.api

import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import ru.iqsklad.data.web.response.UsersResponse

interface UsersApi {

    //method = "person.getList"
    @POST("eqar")
    fun getUsersAsync(@Body body: RequestBody): Deferred<UsersResponse>

    //method = "person.getChange"
    @POST("eqar")
    fun getUsersChangesAsync(@Body body: RequestBody): Deferred<UsersResponse>
}