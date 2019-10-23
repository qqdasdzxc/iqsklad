package ru.iqsklad.data.db.convert

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import ru.iqsklad.data.dto.equipment.RFID_EPC
import ru.iqsklad.utils.ConverterUtils

object ScannedRfidsConverter {

    @TypeConverter
    @JvmStatic
    fun fromScannedRfids(data: List<RFID_EPC>?): String? {
        return ConverterUtils.getGsonConverter().toJsonTree(data).toString()
    }

    @TypeConverter
    @JvmStatic
    fun toScannedRfids(data: String?): List<RFID_EPC>? {
        return ConverterUtils.getGsonConverter().fromJson(data, object : TypeToken<List<RFID_EPC>?>() {}.type)
    }
}