package ru.iqsklad.presentation.implementation.user

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.data.dto.ui.UiModel
import ru.iqsklad.data.dto.user.UserType
import ru.iqsklad.data.dto.user.UserUI
import ru.iqsklad.data.dto.user.UsersMapper
import ru.iqsklad.data.repository.contract.IMainRepository
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.user.ChooseUserPresenter
import ru.iqsklad.utils.extensions.getTimeString
import ru.iqsklad.utils.extensions.mapToResult
import ru.iqsklad.utils.pref.LastUpdatePreferences
import javax.inject.Inject

class ChooseUserViewModel: ViewModel(), ChooseUserPresenter {

    @Inject
    lateinit var mainRepository: IMainRepository
    @Inject
    lateinit var procedureDataHolder: ProcedureDataHolder

    private val searchStringLiveData = MutableLiveData<String>()
    private lateinit var lastUpdated: String

    private val forwardersLiveData = Transformations.switchMap(searchStringLiveData) {
        getUsers(UserType.Forwarder, lastUpdated)
    }

    private val stewardsLiveData = Transformations.switchMap(searchStringLiveData) {
        getUsers(UserType.Steward, lastUpdated)
    }

    init {
        App.appComponent.inject(this)
        searchStringLiveData.postValue("")
    }

    override fun getForwarders(context: Context): LiveData<UiModel<List<UserUI>>> {
        saveTime(context)
        return forwardersLiveData
    }

    override fun getStewards(context: Context): LiveData<UiModel<List<UserUI>>> {
        saveTime(context)
        return stewardsLiveData
    }

    private fun saveTime(context: Context) {
        lastUpdated = LastUpdatePreferences.getTime(context).getTimeString()
    }

    override fun onSearchTextChanged(searchText: String) {
        searchStringLiveData.postValue(searchText)
    }

    private fun getUsers(userType: UserType, lastUpdated: String): LiveData<UiModel<List<UserUI>>> {
        return mainRepository.getUsers(
            type = userType,
            searchString = searchStringLiveData.value!!.trim(),
            lastUpdated = lastUpdated
        ).mapToResult(
            { SuccessUiModel(UsersMapper.mapUsersForShowFirstLetter(it.data!!.users)) },
            { ErrorUiModel(it.message) }
        )
    }

    override fun getProcedureType(): ProcedureType = procedureDataHolder.procedureType
}