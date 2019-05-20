package ru.iqsklad.presentation.implementation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.user.UserMapper
import ru.iqsklad.data.dto.user.UserUI
import ru.iqsklad.data.repository.contract.IForwardersRepository
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.auth.ChooseForwarderPresenter
import javax.inject.Inject


class ChooseForwarderViewModel @Inject constructor(private var forwardersRepository: IForwardersRepository) :
    ViewModel(), ChooseForwarderPresenter {

    private val searchStringLiveData = MutableLiveData<String>()

    private val forwardersLiveData = Transformations.switchMap(searchStringLiveData) {
        Transformations.map(forwardersRepository.getForwarders(searchStringLiveData.value!!)) {
            return@map UserMapper.mapUsers(it)
        }
    }

    init {
        App.authComponent!!.inject(this)
        searchStringLiveData.postValue("")
    }

    override fun getForwarders(): LiveData<List<UserUI>> = forwardersLiveData

    override fun onSearchTextChanged(searchText: String) {
        searchStringLiveData.postValue(searchText)
    }
}