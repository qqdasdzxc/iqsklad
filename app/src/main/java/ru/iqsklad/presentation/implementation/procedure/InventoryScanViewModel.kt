package ru.iqsklad.presentation.implementation.procedure

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.equipment.Equipment
import ru.iqsklad.data.dto.equipment.RFID_EPC
import ru.iqsklad.data.dto.procedure.*
import ru.iqsklad.data.scan.Scanner
import ru.iqsklad.data.scan.ScannerFactory
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.InventoryScanPresenter
import javax.inject.Inject

class InventoryScanViewModel: ViewModel(), InventoryScanPresenter {

    @Inject
    lateinit var procedureDataHolder: ProcedureDataHolder
    @Inject
    lateinit var scannerFactory: ScannerFactory

    private var scanner: Scanner? = null
    private val invoiceIDObservable = ObservableField<String>()
    private val invoiceInventoryListObservable = MutableLiveData<List<Equipment>>()
    private val inventoryViewModeObservable = ObservableField<EquipmentScanMode>()
    private val errorObservable = MutableLiveData<String>()
    private val inventoryOverAllScannedCount = ObservableField(0)

    private val scannedRfidSet = HashSet<RFID_EPC>()

    init {
        App.appComponent.inject(this)
        scanner = scannerFactory.createScanner()
        procedureDataHolder.procedureInvoice?.setInitState()
        invoiceIDObservable.set(procedureDataHolder.procedureInvoice?.id)
        invoiceInventoryListObservable.value = procedureDataHolder.procedureInvoice?.equipmentList
        inventoryViewModeObservable.set(EquipmentScanMode.PREVIEW)
    }

    override fun getErrorLiveData(): LiveData<String> = errorObservable

    override fun getInvoiceNumber(): ObservableField<String> = invoiceIDObservable

    override fun getInvoiceInventoryLiveData(): LiveData<List<Equipment>> = invoiceInventoryListObservable

    override fun getEquipmentScanMode(): ObservableField<EquipmentScanMode> = inventoryViewModeObservable

    override fun getProcedureType(): ProcedureType = procedureDataHolder.procedureType

    override fun getOverAllScanCount(): ObservableField<Int> = inventoryOverAllScannedCount

    override fun startScan(): LiveData<ScanResult?>? {
        scanner?.let {
            inventoryViewModeObservable.set(EquipmentScanMode.SCANNING)
            return Transformations.map(it.startScan()) { result ->
                return@map processRfid(result)
            }
        }

        errorObservable.postValue("Устройство сканирования не найдено")

        return null
    }

    override fun stopScan() {
        if (inventoryViewModeObservable.get() == EquipmentScanMode.SCANNING) {
            inventoryViewModeObservable.set(EquipmentScanMode.STOPPED)
        }
        scanner?.stopScan()
    }

    private fun processRfid(rfid: RFID_EPC?): ScanResult? {
        rfid?.let { nonNullRfid ->
            if (!scannedRfidSet.contains(nonNullRfid)) {
                inventoryOverAllScannedCount.set(inventoryOverAllScannedCount.get()!! + 1)
                scannedRfidSet.add(nonNullRfid)

                return if (procedureDataHolder.procedureInvoice!!.increaseScannedCountIfContains(nonNullRfid)) {
                    ScanResult(nonNullRfid, ScanResultType.SUCCESS, invoiceIDObservable.get()!!)
                } else {
                    ScanResult(nonNullRfid, ScanResultType.EXCLUDED, invoiceIDObservable.get()!!)
                }
            }
        }

        return null
    }
}