package ru.iqsklad.presentation.implementation.procedure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.data.dto.user.UserMapper
import ru.iqsklad.data.dto.user.UserUI
import ru.iqsklad.data.repository.contract.IStewardsRepository
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.ChooseStewardPresenter
import javax.inject.Inject

class ChooseStewardViewModel @Inject constructor(
    private var stewardsRepository: IStewardsRepository,
    private var procedureDataHolder: ProcedureDataHolder
) : ViewModel(),
    ChooseStewardPresenter {

    private val searchTextLiveData = MutableLiveData<String>()

    private val stewardsLiveData = Transformations.switchMap(searchTextLiveData) {
        Transformations.map(stewardsRepository.getStewards(searchTextLiveData.value!!)) {
            UserMapper.mapUsers(it)
        }
    }

    init {
        App.procedureComponent!!.inject(this)
        searchTextLiveData.postValue("")
    }

    override fun getProcedureType(): ProcedureType = procedureDataHolder.procedureType

    override fun getStewards(): LiveData<List<UserUI>> = stewardsLiveData

    override fun onSearchTextChanged(searchText: String) {
        searchTextLiveData.postValue(searchText)
    }
}