package ru.iqsklad.data.dto.procedure

class ScanResult (
    val rfid: RFID_EPC,
    val scanResultType: ScanResultType,
    val invoiceNumber: String
)