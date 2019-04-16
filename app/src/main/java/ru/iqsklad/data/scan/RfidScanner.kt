package ru.iqsklad.data.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.iqsklad.data.dto.procedure.RFID_EPC

class RfidScanner : IRfidScanner {

    private val rfidLiveData = MutableLiveData<RFID_EPC>()

    override fun getRfidLiveData(): LiveData<RFID_EPC> {
        startScanning()
        return rfidLiveData
    }

    private fun startScanning() {
        rfidLiveData.postValue("E2801170000002071423DD04")
    }
}