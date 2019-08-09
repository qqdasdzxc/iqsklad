package ru.iqsklad.utils.extensions

import com.senter.iot.support.openapi.uhf.UhfD2
import ru.iqsklad.data.dto.procedure.RFID_EPC

fun UhfD2.UII.convertToEPC(): RFID_EPC? {
    if (bytes != null) {
        val sBuffer = StringBuffer()
        for (i in bytes.indices) {
            sBuffer.append(String.format("%02x", bytes[i]))
        }
        return sBuffer.substring(4).toString().toUpperCase()
    }
    return null
}