package ru.iqsklad.presentation.presenter.procedure

import android.content.Context
import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.procedure.Invoice
import ru.iqsklad.data.dto.ui.UiModel

interface FindInvoicePresenter {

    fun refreshInvoices(context: Context)

    fun findInvoice(invoiceID: String)

    fun getFindingInvoiceResult(): LiveData<UiModel<Invoice?>>

    fun setProcedureInvoice(invoice: Invoice)
}