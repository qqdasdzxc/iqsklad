package ru.iqsklad.ui.base.bottom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import ru.iqsklad.R
import ru.iqsklad.data.dto.dbstatus.DatabaseStatus
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.LoadingUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.databinding.FragmentStatusBinding
import ru.iqsklad.presentation.implementation.update.UpdateViewModel
import ru.iqsklad.presentation.presenter.update.UpdatePresenter
import ru.iqsklad.ui.base.fragment.BaseRoundedBottomSheetDialogFragment
import ru.iqsklad.utils.extensions.getTimeString
import ru.iqsklad.utils.pref.LastUpdatePreferences

class StatusFragment: BaseRoundedBottomSheetDialogFragment<FragmentStatusBinding>() {

    private lateinit var presenter: UpdatePresenter

    companion object {
        private val TAG: String = StatusFragment::class.java.canonicalName!!

        fun getInstance(): StatusFragment = StatusFragment()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_status

    fun show(manager: FragmentManager) {
        showSafe(manager, TAG)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = getPresenter<UpdateViewModel>()
        updateState()
    }

    private fun updateState() {
        val needToRefresh = presenter.needToRefreshDB(activity!!)
        binding.dbStatus = if (needToRefresh) DatabaseStatus.NotUpdated else DatabaseStatus.Updated
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurationView()
    }

    private fun configurationView() {
        binding.resumeWorkActionView.setOnClickListener { dismiss() }

        binding.updateActionView.setOnClickListener { updateDb() }

        setLastUpdatedTime()
    }

    private fun updateDb() {
        isCancelable = false
        presenter.updateAllData(activity!!).observe(this, Observer { uiModel ->
            when (uiModel) {
                LoadingUiModel -> binding.dbStatus = DatabaseStatus.Updating
                is SuccessUiModel -> {
                    isCancelable = true
                    updateState()
                    setLastUpdatedTime()
                }
                is ErrorUiModel -> {
                    isCancelable = true
                    showMessage(uiModel.error)
                    updateState()
                }
            }
        })
    }

    private fun setLastUpdatedTime() {
        binding.statusLastUpdateTextView.text = getString(
            R.string.status_last_update_time,
            LastUpdatePreferences.getTime(activity!!).getTimeString()
        )
    }
}