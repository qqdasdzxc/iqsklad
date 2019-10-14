package ru.iqsklad.ui.base.bottom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import ru.iqsklad.R
import ru.iqsklad.databinding.FragmentStatusBinding
import ru.iqsklad.presentation.implementation.update.UpdateViewModel
import ru.iqsklad.presentation.presenter.update.UpdatePresenter
import ru.iqsklad.ui.base.fragment.BaseRoundedBottomSheetDialogFragment

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

        presenter = getPresenter<UpdateViewModel>().apply {
            updateState(needToRefreshDB(activity!!))
        }
    }

    private fun updateState(needToRefreshDB: Boolean) {
        //TODO
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurationView()
    }

    private fun configurationView() {
        binding.resumeWorkActionView.setOnClickListener { dismiss() }
    }
}