package ru.iqsklad.presentation.presenter.procedure

import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.user.User

interface ChooseStewardPresenter {

    fun getStewards(): LiveData<List<User>>

    fun onSearchTextChanged(searchText: String)
}