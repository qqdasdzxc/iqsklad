package ru.iqsklad.data.scan

//import com.rscja.deviceapi.RFIDWithUHF

class RFIDWithUHFScanner {// : Scanner() {

//    private var scanner: RFIDWithUHF? = null
//
//    override fun startInventoryTagInNeeded() {
//        RFIDWithUHF.getInstance().free()
//        scanner = RFIDWithUHF.getInstance().apply {
//            //init()
//            startInventoryTag(0,0)
//        }
//
//    }
//
//    override fun getInventory(): RFID_EPC? {
//        scanner?.readTagFromBuffer()?.let { resultScanArray ->
//            return scanner?.convertUiiToEPC(resultScanArray[1])
//        }
//        return null
//    }
//
//    override fun stopInventory() {
//        scanner?.stopInventory()
//        scanner?.free()
//        scanner = null
//    }
}