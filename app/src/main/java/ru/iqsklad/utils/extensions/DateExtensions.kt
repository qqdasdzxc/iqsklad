package ru.iqsklad.utils.extensions

import ru.iqsklad.data.Constants.MILLISECONDS_EXPIRED
import java.text.SimpleDateFormat
import java.util.*

private const val REQUEST_DATE_FORMAT = "yyyy-MM-dd"
private const val UI_DATE_FORMAT = "yyyy.MM.dd HH:mm"

private var requestDateFormatter = SimpleDateFormat(REQUEST_DATE_FORMAT, Locale.getDefault())
private var uiDateFormatter = SimpleDateFormat(UI_DATE_FORMAT, Locale.getDefault())

fun Date.requestDateFormat(): String = requestDateFormatter.format(this)

fun Date.uiDateFormat(): String = uiDateFormatter.format(this)

fun getCurrentDate(): String = Date(Calendar.getInstance().timeInMillis).requestDateFormat()

fun getTimeInMillis() = Calendar.getInstance().timeInMillis

fun Calendar.isRefreshTimeExpired(pastCalendar: Calendar): Boolean {
    return timeInMillis > pastCalendar.timeInMillis + MILLISECONDS_EXPIRED
}

fun Calendar.getLastUpdatedTime(): String = Date(timeInMillis).requestDateFormat()

fun Long.getTimeString(): String {
    return Date(this).uiDateFormat()
}