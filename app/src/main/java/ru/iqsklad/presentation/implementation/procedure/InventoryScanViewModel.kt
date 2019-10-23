package ru.iqsklad.presentation.implementation.procedure

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import ru.iqsklad.data.dto.equipment.Equipment
import ru.iqsklad.data.dto.equipment.RFID_EPC
import ru.iqsklad.data.dto.procedure.*
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.data.dto.ui.UiModel
import ru.iqsklad.data.repository.contract.IMainRepository
import ru.iqsklad.data.scan.Scanner
import ru.iqsklad.data.scan.ScannerFactory
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.InventoryScanPresenter
import ru.iqsklad.utils.extensions.mapToResult
import java.util.*
import javax.inject.Inject

class InventoryScanViewModel : ViewModel(), InventoryScanPresenter {

    @Inject
    lateinit var procedureDataHolder: ProcedureDataHolder
    @Inject
    lateinit var scannerFactory: ScannerFactory
    @Inject
    lateinit var repository: IMainRepository

    private var scanner: Scanner? = null
    private val invoiceIDObservable = ObservableField<String>()
    private val invoiceInventoryListObservable = MutableLiveData<List<Equipment>>()
    private val inventoryViewModeObservable = ObservableField<EquipmentScanMode>()
    private val errorObservable = MutableLiveData<String>()
    private val inventoryOverAllScannedCount = ObservableField(0)
    private val updateScanResultLiveData = MutableLiveData<ScanResult>()

    private val rfidInfoToLoadQueue = ArrayDeque<RFID_EPC>()
    private var gettingRfidInfo = false

    init {
        App.appComponent.inject(this)
        scanner = scannerFactory.createScanner()
        procedureDataHolder.setInitState()
        invoiceIDObservable.set(procedureDataHolder.procedureInvoice?.id)
        invoiceInventoryListObservable.value = procedureDataHolder.procedureInvoice?.equipmentList
        inventoryViewModeObservable.set(EquipmentScanMode.PREVIEW)
    }

    override fun onCleared() {
        super.onCleared()

        scanner?.turnOff()
    }

    override fun getErrorLiveData(): LiveData<String> = errorObservable

    override fun getInvoiceNumber(): ObservableField<String> = invoiceIDObservable

    override fun getInvoiceInventoryLiveData(): LiveData<List<Equipment>> =
        invoiceInventoryListObservable

    override fun getEquipmentScanMode(): ObservableField<EquipmentScanMode> =
        inventoryViewModeObservable

    override fun getProcedureType(): ProcedureType = procedureDataHolder.procedureType

    override fun getOverAllScanCount(): ObservableField<Int> = inventoryOverAllScannedCount

    override fun getUpdateScanResult(): LiveData<ScanResult> = updateScanResultLiveData

    @InternalCoroutinesApi
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
            if (!procedureDataHolder.scannedRfidSet.contains(nonNullRfid)) {
                inventoryOverAllScannedCount.set(inventoryOverAllScannedCount.get()!! + 1)
                procedureDataHolder.scannedRfidSet.add(nonNullRfid)

                return if (procedureDataHolder.procedureInvoice!!.increaseScannedCountIfContains(nonNullRfid)) {
                    ScanResult(nonNullRfid, ScanResultType.SUCCESS, invoiceIDObservable.get()!!)
                } else {
                    rfidInfoToLoadQueue.addLast(rfid)
                    loadNextRfidInfo(invoiceIDObservable.get()!!)
                    ScanResult(nonNullRfid, ScanResultType.LOADING, invoiceIDObservable.get()!!)
                }
            }
        }

        return null
    }

    //todo refactor on flow
    private fun loadNextRfidInfo(invoiceID: String) {
        if (!gettingRfidInfo) {
            val rfidToScan = rfidInfoToLoadQueue.peek()
            rfidToScan?.let { rfid ->
                gettingRfidInfo = true
                CoroutineScope(Dispatchers.Default).launch {
                    Log.d("start loading", rfid)
                    val rfidResult = repository.getRfidFromDB(epc = rfid)
                    val scanResultType = if (rfidResult == null) ScanResultType.EXCLUDED_FROM_DATABASE else ScanResultType.EXCLUDED_FROM_INVOICE
                    Log.d("end loading", rfid)
                    rfidInfoToLoadQueue.pop()
                    gettingRfidInfo = false
                    loadNextRfidInfo(invoiceID)
                    updateScanResultLiveData.postValue(ScanResult(rfid, scanResultType, invoiceID))
                }
            }
        }
    }

    override fun sendScanResults(): LiveData<UiModel<Boolean>> =
        repository.sendScanResults(procedureDataHolder).mapToResult(
            successAction = {
                SuccessUiModel(true)
            },
            errorAction = {
                ErrorUiModel(it.message)
            }
        )
}