package ru.iqsklad.presentation.presenter.auth

import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.user.UserUI

interface ChooseForwarderPresenter {

    fun getForwarders(): LiveData<List<UserUI>>

    fun onSearchTextChanged(searchText: String)
}