package ru.iqsklad.data.web.api

import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import ru.iqsklad.data.web.response.InvoicesWithEquipmentResponse

interface InvoiceApi {

    //method = "invoice.getList"
    //!!! в параметрах отправить "last_update": "true" !!!
    @POST("eqar")
    fun getInvoicesChangesAsync(@Body body: RequestBody): Deferred<InvoicesWithEquipmentResponse>

    //method = "invoice.getList"
    @POST("eqar")
    fun getInvoicesAsync(@Body body: RequestBody): Deferred<InvoicesWithEquipmentResponse>
}