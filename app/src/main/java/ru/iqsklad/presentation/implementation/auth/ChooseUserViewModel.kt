package ru.iqsklad.presentation.implementation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.repository.contract.IUsersRepository
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.auth.ChooseUserPresenter
import javax.inject.Inject


class ChooseUserViewModel @Inject constructor(private var usersRepository: IUsersRepository) :
    ViewModel(), ChooseUserPresenter {

    init {
        App.authComponent!!.inject(this)
    }

    override fun getUsers(): LiveData<List<User>> = usersRepository.getUsers()

    override fun onSearchTextChanged(searchText: String) {

    }
}