package ru.iqsklad.data.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.iqsklad.data.dto.procedure.RFID_EPC

abstract class Scanner {

    private val rfidLiveData = MutableLiveData<RFID_EPC>()
    private var scanJob = Job()

    fun startScan(): LiveData<RFID_EPC> {
        initScan()
        scanJob.start()

        return rfidLiveData
    }

    fun stopScan() {
        scanJob.cancel()
    }

    private fun initScan() {
        scanJob = CoroutineScope(Dispatchers.IO).async (start = CoroutineStart.LAZY) {
            startInventoryTagInNeeded()

            while (isActive) {
                getInventory()
            }
        }
    }

    abstract fun startInventoryTagInNeeded()

    abstract fun stopInventory()

    abstract fun getInventory()

    protected fun getInventoryLiveData() = rfidLiveData

}