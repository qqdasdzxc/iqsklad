package ru.iqsklad.data.dto.procedure

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.iqsklad.data.dto.equipment.RFID_EPC

@Entity
class ProcedureResult(
    @PrimaryKey(autoGenerate = true)
    val procedureResultID: Int = 0,
    val invoiceID: Int,
    val procedureTypeID: Int,
    val scannedRfids: List<RFID_EPC>
)