package ru.iqsklad.presentation.presenter.procedure

import ru.iqsklad.data.dto.procedure.ProcedureType

interface ProcedureSuccessPresenter {

    fun getProcedureType(): ProcedureType

    fun getInvoiceNumber(): String
}