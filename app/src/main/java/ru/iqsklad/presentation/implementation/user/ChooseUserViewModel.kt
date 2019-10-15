package ru.iqsklad.presentation.implementation.user

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
import ru.iqsklad.utils.extensions.mapToResult
import javax.inject.Inject

class ChooseUserViewModel: ViewModel(), ChooseUserPresenter {

    @Inject
    lateinit var mainRepository: IMainRepository
    @Inject
    lateinit var procedureDataHolder: ProcedureDataHolder

    private val searchStringLiveData = MutableLiveData<String>()

    private val forwardersLiveData = Transformations.switchMap(searchStringLiveData) {
        getUsers(UserType.Forwarder)
    }

    private val stewardsLiveData = Transformations.switchMap(searchStringLiveData) {
        getUsers(UserType.Steward)
    }

    init {
        App.appComponent.inject(this)
        searchStringLiveData.postValue("")
    }

    override fun getForwarders(): LiveData<UiModel<List<UserUI>>> = forwardersLiveData

    override fun getStewards(): LiveData<UiModel<List<UserUI>>> = stewardsLiveData

    override fun onSearchTextChanged(searchText: String) {
        searchStringLiveData.postValue(searchText)
    }

    private fun getUsers(userType: UserType): LiveData<UiModel<List<UserUI>>> {
        return mainRepository.getUsersWithChanges(
            type = userType,
            searchString = searchStringLiveData.value!!.trim()
        ).mapToResult(
            { SuccessUiModel(UsersMapper.mapUsersForShowFirstLetter(it.data!!.users)) },
            { ErrorUiModel(it.message) }
        )
    }

    override fun getProcedureType(): ProcedureType = procedureDataHolder.procedureType
}