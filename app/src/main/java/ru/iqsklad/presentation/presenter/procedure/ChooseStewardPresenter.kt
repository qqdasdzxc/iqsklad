package ru.iqsklad.presentation.presenter.procedure

import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.data.dto.user.UserUI

interface ChooseStewardPresenter {

    fun getStewards(): LiveData<List<UserUI>>

    fun getProcedureType(): ProcedureType

    fun onSearchTextChanged(searchText: String)
}