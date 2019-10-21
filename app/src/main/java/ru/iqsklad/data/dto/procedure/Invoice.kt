package ru.iqsklad.data.dto.procedure

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.iqsklad.data.dto.equipment.Equipment

@Entity
class Invoice(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("view_number")
    val viewNumber: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("statusTitle")
    var statusTitle: String? = null,
    @SerializedName("when_created")
    val whenCreated: String,
    @SerializedName("items")
    val equipmentList: List<Equipment>
) {
    fun increaseScannedCountIfContains(rfid: String): Boolean {
        for (equipment in equipmentList) {
            equipment.rfids?.let {
                if (it.contains(rfid)) {
                    equipment.scannedCount++
                    return true
                }
            }
        }
        return false
    }

    fun setInitState() {
        for (equipment in equipmentList) {
            equipment.scannedCount = 0
        }
    }
}