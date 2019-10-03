package ru.iqsklad.data.dto.equipment

import com.google.gson.annotations.SerializedName

class EquipmentType(
    @SerializedName("equipment")
    val equipmentID: String,
    @SerializedName("title")
    val title: String
)