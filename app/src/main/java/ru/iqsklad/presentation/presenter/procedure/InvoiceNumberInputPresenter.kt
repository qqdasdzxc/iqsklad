package ru.iqsklad.presentation.presenter.procedure

import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.presentation.observable.TextField

interface InvoiceNumberInputPresenter {

    fun getInvoiceNumberObservable(): TextField

    fun sendInvoiceNumber()

    fun getAcceptInvoiceNumber(): LiveData<Boolean>

    fun initAcceptInvoiceNumber()

    fun getProcedureType(): ProcedureType
}