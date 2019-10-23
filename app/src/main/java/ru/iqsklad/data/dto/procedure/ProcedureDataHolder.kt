package ru.iqsklad.data.dto.procedure

import ru.iqsklad.data.dto.equipment.RFID_EPC

class ProcedureDataHolder {
    var procedureType = ProcedureType.PASS
    var procedureInvoice: Invoice? = null
    val scannedRfidSet = HashSet<RFID_EPC>()

    fun setInitState() {
        procedureInvoice?.setInitState()
        scannedRfidSet.clear()
    }
}