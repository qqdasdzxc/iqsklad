package ru.iqsklad.data.dto.procedure

typealias RFID_EPC = String

class Inventory (
    val inventoryID: Int,
    val title: String,
    val plannedCount: Int,
    var scannedCount: Int = 0,
    val rfidList: List<RFID_EPC>
)