package ru.iqsklad.data.dto.rfid

import com.google.gson.annotations.SerializedName

class Rfid(
    @SerializedName("rfidID")
    val rfidID: String,
    @SerializedName("equipmentID")
    val equipmentID: String
)