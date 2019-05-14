package ru.iqsklad.ui.procedure.fragment.bottom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseRoundedBottomSheetDialogFragment
import ru.iqsklad.databinding.FragmentStatusBinding

class StatusFragment: BaseRoundedBottomSheetDialogFragment<FragmentStatusBinding>() {

    companion object {
        private val TAG: String = StatusFragment::class.java.canonicalName!!

        fun getInstance(): StatusFragment =
            StatusFragment()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_status

    fun show(manager: FragmentManager) {
        showSafe(manager, TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurationView()
    }

    private fun configurationView() {
        binding.resumeWorkActionView.setOnClickListener { dismiss() }
    }
}