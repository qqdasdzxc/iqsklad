package ru.iqsklad.presentation.implementation.procedure

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.*
import ru.iqsklad.data.scan.ScannerFactory
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.InventoryScanPresenter
import javax.inject.Inject

class InventoryScanViewModel @Inject constructor(
    private var procedureDataHolder: ProcedureDataHolder,
    scannerFactory: ScannerFactory
) : ViewModel(), InventoryScanPresenter {

    private val scanner = scannerFactory.createScanner()
    private val invoiceIDObservable = ObservableField<String>()
    private val invoiceInventoryListObservable = MutableLiveData<List<Inventory>>()
    private val inventoryViewModeObservable = ObservableField<InventoryScanMode>()
    private val errorObservable = MutableLiveData<String>()
    //private val inventoryOverAllScannedCount = ObservableField(0)

    private val scannedRfidSet = HashSet<RFID_EPC>()

    init {
        App.procedureComponent!!.inject(this)
        procedureDataHolder.procedureInvoice.setInitState()
        invoiceIDObservable.set(procedureDataHolder.procedureInvoice.invoiceID)
        invoiceInventoryListObservable.value = procedureDataHolder.procedureInvoice.inventoryList
        inventoryViewModeObservable.set(InventoryScanMode.PREVIEW)
    }

    override fun getErrorLiveData(): LiveData<String> = errorObservable

    override fun getInvoiceNumber(): ObservableField<String> = invoiceIDObservable

    override fun getInvoiceInventoryLiveData(): LiveData<List<Inventory>> = invoiceInventoryListObservable

    override fun getInventoryScanMode(): ObservableField<InventoryScanMode> = inventoryViewModeObservable

    override fun getProcedureType(): ProcedureType = procedureDataHolder.procedureType

    //override fun getOverAllScanCount(): ObservableField<Int> = inventoryOverAllScannedCount

    override fun startScan(): LiveData<ScanResult?>? {
        scanner?.let {
            inventoryViewModeObservable.set(InventoryScanMode.SCANNING)
            return Transformations.map(it.startScan()) { result ->
                return@map processRfid(result)
            }
        }

        errorObservable.postValue("Устройство сканирования не найдено")

        return null
    }

    override fun stopScan() {
        inventoryViewModeObservable.set(InventoryScanMode.STOPPED)
        scanner?.stopScan()
    }

    private fun processRfid(rfid: RFID_EPC?): ScanResult? {
        rfid?.let { nonNullRfid ->
            if (!scannedRfidSet.contains(nonNullRfid)) {
                //inventoryOverAllScannedCount.set(inventoryOverAllScannedCount.get()!! + 1)
                scannedRfidSet.add(nonNullRfid)
                return if (procedureDataHolder.procedureInvoice.increaseScannedCountIfContains(nonNullRfid)) {
                    ScanResult(nonNullRfid, ScanResultType.SUCCESS, invoiceIDObservable.get()!!)
                } else {
                    ScanResult(nonNullRfid, ScanResultType.EXCLUDED, invoiceIDObservable.get()!!)
                }
            }
        }

        return null
    }
}