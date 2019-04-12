package ru.iqsklad.presentation.presenter.auth

import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.user.User

interface ChooseUserPresenter {

    fun getUsers(): LiveData<List<User>>

    fun onSearchTextChanged(searchText: String)
}