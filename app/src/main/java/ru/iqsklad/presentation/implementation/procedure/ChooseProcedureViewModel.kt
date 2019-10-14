package ru.iqsklad.presentation.implementation.procedure

import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.ChooseProcedurePresenter
import javax.inject.Inject

class ChooseProcedureViewModel : ViewModel(), ChooseProcedurePresenter {

    @Inject
    lateinit var procedureDataHolder: ProcedureDataHolder

    init {
        App.appComponent.inject(this)
    }

    override fun setProcedureType(procedureType: ProcedureType) {
        procedureDataHolder.procedureType = procedureType
    }
}