package ru.iqsklad.data.dto.rfid

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Rfid(
    @PrimaryKey
    @SerializedName("rfidID")
    val rfidID: String,
    @SerializedName("equipmentID")
    val equipmentID: String,
    @SerializedName("equipmentTypeTitle")
    var equipmentTypeTitle: String? = null
)