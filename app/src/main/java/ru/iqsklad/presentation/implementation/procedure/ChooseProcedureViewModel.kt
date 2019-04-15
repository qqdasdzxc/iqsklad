package ru.iqsklad.presentation.implementation.procedure

import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.ChooseProcedurePresenter
import javax.inject.Inject

class ChooseProcedureViewModel @Inject constructor(private var procedureDataHolder: ProcedureDataHolder) : ViewModel(),
    ChooseProcedurePresenter {

    init {
        App.procedureComponent!!.inject(this)
    }

    override fun setProcedureType(procedureType: ProcedureType) {
        procedureDataHolder.procedureType = procedureType
    }
}