package ru.iqsklad.presentation.implementation.procedure


import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.ProcedureSuccessPresenter
import javax.inject.Inject

class ProcedureSuccessViewModel @Inject constructor(private var procedureDataHolder: ProcedureDataHolder) : ViewModel(),
    ProcedureSuccessPresenter {

    init {
        App.procedureComponent!!.inject(this)
    }

    override fun getProcedureType(): ProcedureType = procedureDataHolder.procedureType

    override fun getInvoiceNumber(): String = procedureDataHolder.procedureInvoice.invoiceID!!
}