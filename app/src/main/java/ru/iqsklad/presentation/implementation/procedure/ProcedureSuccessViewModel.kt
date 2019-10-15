package ru.iqsklad.presentation.implementation.procedure


import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.ProcedureSuccessPresenter
import javax.inject.Inject

class ProcedureSuccessViewModel : ViewModel(), ProcedureSuccessPresenter {

    @Inject
    lateinit var procedureDataHolder: ProcedureDataHolder

    init {
        App.appComponent.inject(this)
    }

    override fun getProcedureType(): ProcedureType = procedureDataHolder.procedureType

    override fun getInvoiceNumber(): String = "asd"//procedureDataHolder.procedureInvoice.invoiceID!!
}