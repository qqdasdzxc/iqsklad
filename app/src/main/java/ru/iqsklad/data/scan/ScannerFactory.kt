package ru.iqsklad.data.scan

//import com.rscja.deviceapi.RFIDWithUHF
import com.senter.iot.support.openapi.uhf.UhfD2

class ScannerFactory {

    fun createScanner(): Scanner? = when {
        //createRFIDWithUHFDevice() -> RFIDWithUHFScanner()
        createUhfD2Device() -> UhfD2Scanner()
        else -> null
    }

    private fun createRFIDWithUHFDevice(): Boolean {
//        return try {
//            RFIDWithUHF.getInstance().init()
//        } catch (e: Exception) {
//            false
//        }
        return false
    }

    private fun createUhfD2Device(): Boolean {
        return try {
            UhfD2.getInstance().init() || UhfD2.getInstance().reset()
        } catch (e: IllegalStateException) {
            UhfD2.getInstance().reset()
        } catch (e: Exception) {
            false
        } finally {
            UhfD2.getInstance().uninit()
        }
    }
}