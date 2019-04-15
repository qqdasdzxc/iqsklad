package ru.iqsklad.presentation.implementation.procedure

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.repository.contract.IStewardsRepository
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.procedure.ChooseStewardPresenter
import javax.inject.Inject

class ChooseStewardViewModel @Inject constructor(private var stewardsRepository: IStewardsRepository) : ViewModel(),
    ChooseStewardPresenter {

    init {
        App.procedureComponent!!.inject(this)
    }

    override fun getStewards(): LiveData<List<User>> {
        return stewardsRepository.getStewards()
    }

    override fun onSearchTextChanged(searchText: String) {

    }
}