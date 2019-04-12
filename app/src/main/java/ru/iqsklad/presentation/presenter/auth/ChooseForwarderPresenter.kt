package ru.iqsklad.presentation.presenter.auth

import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.user.User

interface ChooseForwarderPresenter {

    fun getForwarders(): LiveData<List<User>>

    fun onSearchTextChanged(searchText: String)
}