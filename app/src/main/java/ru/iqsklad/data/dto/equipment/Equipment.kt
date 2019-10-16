package ru.iqsklad.data.dto.equipment

import com.google.gson.annotations.SerializedName
typealias RFID_EPC = String

class Equipment(
    @SerializedName("equipment")
    val equipmentID: String,
    @SerializedName("equipmentTitle")
    var equipmentTitle: String? = null,
    @SerializedName("count")
    val count: String,
    @SerializedName("count_created")
    val countCreated: String,
    @SerializedName("count_stock_in")
    val countStockIn: String? = null,
    @SerializedName("count_stock_out")
    val countStockOut: String? = null,
    @SerializedName("rfid_ids")
    val rfids: List<RFID_EPC>
) {
    var scannedCount: Int = 0
}