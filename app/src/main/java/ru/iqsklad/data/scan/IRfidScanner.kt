package ru.iqsklad.data.scan

import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.procedure.RFID_EPC

interface IRfidScanner {

    fun startScan(): LiveData<RFID_EPC>

    fun stopScan()
}