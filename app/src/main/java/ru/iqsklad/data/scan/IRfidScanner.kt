package ru.iqsklad.data.scan

import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.procedure.RFID_EPC

interface IRfidScanner {

    fun getRfidLiveData(): LiveData<RFID_EPC>
}