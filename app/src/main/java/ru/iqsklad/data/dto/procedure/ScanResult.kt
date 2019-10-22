package ru.iqsklad.data.dto.procedure

import ru.iqsklad.data.dto.equipment.RFID_EPC

class ScanResult (
    val rfid: RFID_EPC,
    val scanResultType: ScanResultType,
    val invoiceNumber: String
) {
    override fun equals(other: Any?): Boolean {
        return rfid == (other as ScanResult).rfid
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}