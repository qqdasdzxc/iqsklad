package ru.iqsklad.domain

import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.iqsklad.data.db.dao.MainDao
import ru.iqsklad.data.dto.procedure.ProcedureResult
import ru.iqsklad.data.web.api.MainApi
import ru.iqsklad.data.web.factory.RequestBuilder

class ProcedureResultsSender(
    private var api: MainApi,
    private var dao: MainDao,
    private var context: Context,
    private var requestBuilder: RequestBuilder
): Runnable {

    companion object {
        const val DELAY_BETWEEN_SEND = 300000L //5 min
    }

    private val handler = Handler()

    init {
        startSendingResults()
    }

    private fun startSendingResults() = handler.post(this)

    override fun run() {
        if (connectedToNet()) {
            CoroutineScope(Dispatchers.IO).launch {
                val savedProcedureResults = dao.getProcedureResults()
                savedProcedureResults.forEach {
                    sendProcedureResult(it)
                }
                resumeWorkAfterDelay()
            }
        } else {
            resumeWorkAfterDelay()
        }
    }

    private fun connectedToNet(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected ?: false
    }

    private suspend fun sendProcedureResult(procedureResult: ProcedureResult) {
        try {
            api.sendScanEquipmentResults(
                requestBuilder
                    .createRequest()
                    .setMethod("invoice.send")
                    .setParams(
                        mapOf(
                            Pair("invoice", procedureResult.invoiceID),
                            Pair("type", procedureResult.procedureTypeID),
                            Pair("serials", procedureResult.scannedRfids.toString())
                        )
                    )
                    .build()
            ).await()
            //todo check result
            //если дошли до этого места, значит запрос ушел без ошибок
            dao.deleteProcedureResult(procedureResult.procedureResultID)
        } catch (exception: Exception) {

        }
    }

    private fun resumeWorkAfterDelay() = handler.postDelayed(this, DELAY_BETWEEN_SEND)
}