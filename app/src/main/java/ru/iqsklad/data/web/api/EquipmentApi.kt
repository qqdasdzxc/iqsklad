package ru.iqsklad.data.web.api

import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import ru.iqsklad.data.web.response.UsersResponse

interface EquipmentApi {

    //method - "rfid.getList"
    @POST("eqar")
    fun getEquipmentsAsync(@Body requestBody: RequestBody): Deferred<UsersResponse>

    //method - "rfid.getChange"
    @POST("eqar")
    fun getEquipmentsChangesAsync(@Body requestBody: RequestBody): Deferred<UsersResponse>
}