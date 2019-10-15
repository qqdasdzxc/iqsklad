package ru.iqsklad.presentation.presenter.user

import android.content.Context
import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.data.dto.ui.UiModel
import ru.iqsklad.data.dto.user.UserUI

interface ChooseUserPresenter {

    fun getForwarders(context: Context): LiveData<UiModel<List<UserUI>>>

    fun getStewards(context: Context): LiveData<UiModel<List<UserUI>>>

    fun onSearchTextChanged(searchText: String)

    fun getProcedureType(): ProcedureType
}