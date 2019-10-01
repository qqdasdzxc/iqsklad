package ru.iqsklad.presentation.presenter.procedure

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.procedure.Inventory
import ru.iqsklad.data.dto.procedure.InventoryScanMode
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.data.dto.procedure.ScanResult

interface InventoryScanPresenter {

    fun getErrorLiveData(): LiveData<String>

    fun getInvoiceNumber(): ObservableField<String>

    fun getInvoiceInventoryLiveData(): LiveData<List<Inventory>>

    fun getInventoryScanMode(): ObservableField<InventoryScanMode>

    fun getProcedureType(): ProcedureType

    //fun getOverAllScanCount(): ObservableField<Int>

    fun startScan(): LiveData<ScanResult?>?

    fun stopScan()
}