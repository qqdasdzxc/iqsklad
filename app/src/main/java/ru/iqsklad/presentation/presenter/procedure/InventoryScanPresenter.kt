package ru.iqsklad.presentation.presenter.procedure

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.equipment.Equipment
import ru.iqsklad.data.dto.procedure.EquipmentScanMode
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.data.dto.procedure.ScanResult
import ru.iqsklad.data.dto.ui.UiModel

interface InventoryScanPresenter {

    fun getErrorLiveData(): LiveData<String>

    fun getInvoiceNumber(): ObservableField<String>

    fun getInvoiceInventoryLiveData(): LiveData<List<Equipment>>

    fun getEquipmentScanMode(): ObservableField<EquipmentScanMode>

    fun getProcedureType(): ProcedureType

    fun getOverAllScanCount(): ObservableField<Int>

    fun getUpdateScanResult(): LiveData<ScanResult>

    fun startScan(): LiveData<ScanResult?>?

    fun stopScan()

    fun sendScanResults(): LiveData<UiModel<Boolean>>
}