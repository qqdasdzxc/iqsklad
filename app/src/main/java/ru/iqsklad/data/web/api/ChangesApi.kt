package ru.iqsklad.data.web.api

import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import ru.iqsklad.data.web.response.AllDataChangesResponse

interface ChangesApi {

    //method = "module.getAllChanges"
    @POST("eqar")
    fun getAllChangesAsync(@Body body: RequestBody): Deferred<AllDataChangesResponse>

}