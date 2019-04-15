package ru.iqsklad.presentation.presenter.procedure

import ru.iqsklad.data.dto.procedure.ProcedureType

interface ChooseProcedurePresenter {

    fun setProcedureType(procedureType: ProcedureType)
}