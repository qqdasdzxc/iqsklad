package ru.iqsklad.presentation.implementation.procedure

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.Inventory
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.InvoicePreviewPresenter
import javax.inject.Inject

class InvoicePreviewViewModel @Inject constructor(procedureDataHolder: ProcedureDataHolder) : ViewModel(),
    InvoicePreviewPresenter {

    private val invoiceIDObservable = ObservableField<String>()
    private val invoiceInventoryListObservable = MutableLiveData<List<Inventory>>()

    init {
        App.procedureComponent!!.inject(this)
        invoiceIDObservable.set(procedureDataHolder.procedureInvoice.invoiceID)
        invoiceInventoryListObservable.value = procedureDataHolder.procedureInvoice.inventoryList
    }

    override fun getInvoiceNumber(): ObservableField<String> {
        return invoiceIDObservable
    }

    override fun getInvoiceInventoryLiveData(): LiveData<List<Inventory>> {
        return invoiceInventoryListObservable
    }
}