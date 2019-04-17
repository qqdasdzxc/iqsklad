package ru.iqsklad.data.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rscja.deviceapi.RFIDWithUHF
import kotlinx.coroutines.*
import ru.iqsklad.data.dto.procedure.RFID_EPC

class RfidScanner : IRfidScanner {

    private val rfidLiveData = MutableLiveData<RFID_EPC>()
    private var scanJob = Job()
    private var scanner: RFIDWithUHF? = null

    override fun startScan(): LiveData<RFID_EPC> {
        initScan()
        scanJob.start()
        return rfidLiveData
    }

    override fun stopScan() {
        scanJob.cancel()
        scanner?.stopInventory()
        scanner?.free()
        scanner = null
    }

    private fun initScan() {
        scanner = RFIDWithUHF.getInstance()

        scanJob = CoroutineScope(Dispatchers.IO).async (start = CoroutineStart.LAZY) {
            scanner!!.init()
            scanner!!.startInventoryTag(0,0)

            while (isActive) {
                val scannedStringArray = scanner!!.readTagFromBuffer()
                if (scannedStringArray != null) {
                    val stringEPCResult = scanner!!.convertUiiToEPC(scannedStringArray[1])
                    rfidLiveData.postValue(stringEPCResult)
                }
            }
        }
    }
}