package ru.iqsklad.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

private const val REQUEST_DATE_FORMAT = "yyyy-MM-dd"

private var requestDateFormatter = SimpleDateFormat(REQUEST_DATE_FORMAT, Locale.getDefault())

fun Date.requestDateFormat(): String = requestDateFormatter.format(this)

fun getCurrentDate(): String = Date(Calendar.getInstance().timeInMillis).requestDateFormat()