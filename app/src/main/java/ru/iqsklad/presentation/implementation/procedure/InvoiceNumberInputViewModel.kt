package ru.iqsklad.presentation.implementation.procedure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.observable.TextField
import ru.iqsklad.presentation.presenter.procedure.InvoiceNumberInputPresenter
import javax.inject.Inject

class InvoiceNumberInputViewModel @Inject constructor(private var procedureDataHolder: ProcedureDataHolder) :
    ViewModel(), InvoiceNumberInputPresenter {
    private var invoiceNumberObservable: TextField = TextField()
    private var acceptInvoiceNumber = MutableLiveData<Boolean>()

    init {
        App.procedureComponent!!.inject(this)
    }

    override fun getAcceptInvoiceNumber(): LiveData<Boolean> = acceptInvoiceNumber

    override fun getInvoiceNumberObservable(): TextField = invoiceNumberObservable

    override fun getProcedureType(): ProcedureType = procedureDataHolder.procedureType

    override fun sendInvoiceNumber() {
        if (invoiceNumberObservable.isEmpty()) {
            invoiceNumberObservable.showError()
            return
        }

        procedureDataHolder.procedureInvoice.invoiceID = invoiceNumberObservable.observeEdit.get()
        acceptInvoiceNumber.postValue(true)
    }

    override fun initAcceptInvoiceNumber() {
        acceptInvoiceNumber = MutableLiveData()
    }
}