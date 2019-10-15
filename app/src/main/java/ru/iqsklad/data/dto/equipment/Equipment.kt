package ru.iqsklad.data.dto.equipment

import com.google.gson.annotations.SerializedName

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
    val countStockOut: String? = null
)