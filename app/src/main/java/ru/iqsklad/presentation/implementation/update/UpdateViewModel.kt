package ru.iqsklad.presentation.implementation.update

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.data.dto.ui.UiModel
import ru.iqsklad.data.repository.contract.IMainRepository
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.presenter.update.UpdatePresenter
import ru.iqsklad.utils.extensions.getLastUpdatedTime
import ru.iqsklad.utils.extensions.getTimeInMillis
import ru.iqsklad.utils.extensions.isRefreshTimeExpired
import ru.iqsklad.utils.extensions.mapToResult
import ru.iqsklad.utils.pref.LastUpdatePreferences
import java.util.*
import javax.inject.Inject

class UpdateViewModel : ViewModel(), UpdatePresenter {

    @Inject
    lateinit var repo: IMainRepository

    init {
        App.appComponent.inject(this)
    }

    override fun updateDB(context: Context): LiveData<UiModel<Boolean>> {
        val lastUpdateTime = LastUpdatePreferences.getTime(context)

        return if (lastUpdateTime == LastUpdatePreferences.DEFAULT_LAST_UPDATE_TIME) {
            loadAllData(context)
        } else {
//            if (needToRefreshDB(context)) {
//                loadAllChanges(context)
//            } else {
//                val emptyLiveData = MutableLiveData<UiModel<Boolean>>()
//                emptyLiveData.postValue(SuccessUiModel(true))
//                emptyLiveData
//            }
            loadAllChanges(context)
        }
    }

    private fun loadAllData(context: Context): LiveData<UiModel<Boolean>> =
        repo.loadAllData().mapToResult(
            {
                LastUpdatePreferences.saveTime(context, getTimeInMillis())
                SuccessUiModel(true)
            },
            { ErrorUiModel(it.message) }
        )

    private fun loadAllChanges(context: Context): LiveData<UiModel<Boolean>> {
        val lastUpdateTime = Calendar.getInstance().apply {
            timeInMillis = LastUpdatePreferences.getTime(context)
        }.getLastUpdatedTime()
        return repo.loadAllChanges(lastUpdateTime).mapToResult(
            {
                LastUpdatePreferences.saveTime(context, getTimeInMillis())
                SuccessUiModel(true)
            },
            { ErrorUiModel(it.message) }
        )
    }


    override fun needToRefreshDB(context: Context): Boolean {
        val calendarNow = Calendar.getInstance()
        val calendarPast = Calendar.getInstance().apply {
            timeInMillis = LastUpdatePreferences.getTime(context)
        }
        return calendarNow.isRefreshTimeExpired(calendarPast)
    }

    override fun updateAllData(context: Context): LiveData<UiModel<Boolean>> = loadAllChanges(context)
}