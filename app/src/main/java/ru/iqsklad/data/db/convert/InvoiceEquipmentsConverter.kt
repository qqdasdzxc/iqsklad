package ru.iqsklad.data.db.convert

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import ru.iqsklad.data.dto.equipment.Equipment
import ru.iqsklad.utils.ConverterUtils

object InvoiceEquipmentsConverter {

    @TypeConverter
    @JvmStatic
    fun fromEquipments(data: List<Equipment>?): String? {
        return ConverterUtils.getGsonConverter().toJsonTree(data).toString()
    }

    @TypeConverter
    @JvmStatic
    fun toEquipments(data: String?): List<Equipment>? {
        return ConverterUtils.getGsonConverter().fromJson(data, object : TypeToken<List<Equipment>?>() {}.type)
    }
}