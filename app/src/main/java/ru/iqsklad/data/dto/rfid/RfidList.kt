package ru.iqsklad.data.dto.rfid

import com.google.gson.annotations.SerializedName
import ru.iqsklad.data.dto.equipment.EquipmentType

class RfidList(
    @SerializedName("equipment")
    val equipmentsTypesList: List<EquipmentType>,
    @SerializedName("items")
    val rfidList: List<Rfid>
)