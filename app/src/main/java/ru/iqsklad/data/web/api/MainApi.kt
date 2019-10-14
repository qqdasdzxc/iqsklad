package ru.iqsklad.data.web.api

import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import ru.iqsklad.data.web.response.AllDataChangesResponse
import ru.iqsklad.data.web.response.InvoicesWithEquipmentResponse
import ru.iqsklad.data.web.response.RfidListResponse
import ru.iqsklad.data.web.response.UsersResponse

interface MainApi {

    //method = "person.getList"
    @POST("eqar")
    fun getUsersAsync(@Body body: RequestBody): Deferred<UsersResponse>

    //method = "person.getChange"
    @POST("eqar")
    fun getUsersChangesAsync(@Body body: RequestBody): Deferred<UsersResponse>

    //method = "invoice.getList"
    //!!! в параметрах отправить "last_update": "true" !!!
    @POST("eqar")
    fun getInvoicesChangesAsync(@Body body: RequestBody): Deferred<InvoicesWithEquipmentResponse>

    //method = "invoice.getList"
    @POST("eqar")
    fun getInvoicesAsync(@Body body: RequestBody): Deferred<InvoicesWithEquipmentResponse>

    //method - "rfid.getList"
    @POST("eqar")
    fun getEquipmentsAsync(@Body requestBody: RequestBody): Deferred<RfidListResponse>

    //method - "rfid.getChange"
    @POST("eqar")
    fun getEquipmentsChangesAsync(@Body requestBody: RequestBody): Deferred<RfidListResponse>

    //method = "module.getAllChanges"
    @POST("eqar")
    fun getAllChangesAsync(@Body body: RequestBody): Deferred<AllDataChangesResponse>
}