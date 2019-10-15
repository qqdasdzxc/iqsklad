package ru.iqsklad.presentation.implementation.procedure

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.Invoice
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.data.dto.ui.UiModel
import ru.iqsklad.data.repository.contract.IMainRepository
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.FindInvoicePresenter
import ru.iqsklad.utils.extensions.getTimeString
import ru.iqsklad.utils.extensions.mapToResult
import ru.iqsklad.utils.pref.LastUpdatePreferences
import javax.inject.Inject

class FindInvoiceViewModel: ViewModel(), FindInvoicePresenter {

    @Inject
    lateinit var repository: IMainRepository
    @Inject
    lateinit var procedureDataHolder: ProcedureDataHolder

    private var finding = false

    private val invoiceStringLiveData = MutableLiveData<String>()
    private val invoiceFindResultLiveData = Transformations.switchMap(invoiceStringLiveData) { invoiceString ->
        return@switchMap repository.getInvoice(invoiceString).mapToResult(
            {
                finding = false
                SuccessUiModel(it.data)
            },
            {
                finding = false
                ErrorUiModel(it.message)
            }
        )
    }

    init {
        App.appComponent.inject(this)
    }

    override fun refreshInvoices(context: Context) {
        repository.refreshInvoices(LastUpdatePreferences.getTime(context).getTimeString())
    }

    override fun findInvoice(invoiceID: String) {
        if (!finding) {
            finding = true
            invoiceStringLiveData.postValue(invoiceID)
        }
    }

    override fun getFindingInvoiceResult(): LiveData<UiModel<Invoice?>> = invoiceFindResultLiveData

    override fun setProcedureInvoice(invoice: Invoice) {
        procedureDataHolder.procedureInvoice = invoice
    }
}