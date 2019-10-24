package ru.iqsklad.presentation.presenter.update

import android.content.Context
import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.ui.UiModel

interface UpdatePresenter {

    fun updateDB(context: Context): LiveData<UiModel<Boolean>>

    fun needToRefreshDB(context: Context): Boolean
}