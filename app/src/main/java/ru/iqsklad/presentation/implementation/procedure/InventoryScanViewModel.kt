package ru.iqsklad.presentation.implementation.procedure

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.Inventory
import ru.iqsklad.data.dto.procedure.InventoryScanMode
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.data.dto.procedure.RFID_EPC
import ru.iqsklad.data.scan.IRfidScanner
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.InventoryScanPresenter
import javax.inject.Inject

class InventoryScanViewModel @Inject constructor(
    private var procedureDataHolder: ProcedureDataHolder,
    private var rfidScanner: IRfidScanner
) : ViewModel(),
    InventoryScanPresenter {

    private val invoiceIDObservable = ObservableField<String>()
    private val invoiceInventoryListObservable = MutableLiveData<List<Inventory>>()
    private val inventoryViewModeObservable = ObservableField<InventoryScanMode>()

    private val scannedRfidSet = HashSet<RFID_EPC>()

    init {
        App.procedureComponent!!.inject(this)
        procedureDataHolder.procedureInvoice.setInitState()
        invoiceIDObservable.set(procedureDataHolder.procedureInvoice.invoiceID)
        invoiceInventoryListObservable.value = procedureDataHolder.procedureInvoice.inventoryList
        inventoryViewModeObservable.set(InventoryScanMode.PREVIEW)
    }

    override fun getInvoiceNumber(): ObservableField<String> {
        return invoiceIDObservable
    }

    override fun getInvoiceInventoryLiveData(): LiveData<List<Inventory>> {
        return invoiceInventoryListObservable
    }

    override fun getInventoryScanMode(): ObservableField<InventoryScanMode> {
        return inventoryViewModeObservable
    }

    override fun startScan(): LiveData<RFID_EPC> {
        inventoryViewModeObservable.set(InventoryScanMode.SCANNING)
        return Transformations.map(rfidScanner.startScan()) {
            processRfid(it)
            it
        }
    }

    override fun stopScan() {
        inventoryViewModeObservable.set(InventoryScanMode.STOPPED)
        rfidScanner.stopScan()
    }

    private fun processRfid(rfid: RFID_EPC?) {
        rfid?.let {
            if (!scannedRfidSet.contains(rfid)) {
                scannedRfidSet.add(rfid)
                procedureDataHolder.procedureInvoice.increaseScannedCountIfContains(it)
            }
        }
    }
}