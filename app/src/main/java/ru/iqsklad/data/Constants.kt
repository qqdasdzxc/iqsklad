package ru.iqsklad.data

import ru.iqsklad.utils.extensions.getLastUpdatedTime
import java.util.*

object Constants {

    const val SERVER_URL_API_DEBUG = "https://test.intra.s7.aero/jsonrpc/"
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm"
    const val TIMEOUT_REQUEST = 60L
    const val MILLISECONDS_EXPIRED = 28800000

    val LOAD_ALL_DATA_PARAM = Pair("date", "1970")
    val LOAD_ALL_INVOICES_DATA_PARAM = Pair("date", Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -100)
    }.getLastUpdatedTime())
}