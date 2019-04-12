package ru.iqsklad.presentation.implementation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.repository.contract.IForwardersRepository
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.auth.ChooseForwarderPresenter
import javax.inject.Inject


class ChooseForwarderViewModel @Inject constructor(private var forwardersRepository: IForwardersRepository) :
    ViewModel(), ChooseForwarderPresenter {

    init {
        App.authComponent!!.inject(this)
    }

    override fun getForwarders(): LiveData<List<User>> = forwardersRepository.getForwarders()

    override fun onSearchTextChanged(searchText: String) {

    }
}