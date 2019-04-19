package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentProcedureCancelBinding

class ProcedureCancelFragment: BaseFragment<FragmentProcedureCancelBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_procedure_cancel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val procedureType = arguments!!.get("procedureType")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.procedureCancelActionResumeView.setOnClickListener {
            resumeScanAction()
        }

        binding.procedureCancelActionConfirmView.setOnClickListener {
            navController.navigate(ProcedureCancelFragmentDirections.actionProcedureCancelToChooseProcedure())
        }
    }

    private fun resumeScanAction() {
        navController.navigateUp()
    }

    override fun handleScanPressButton() {
        resumeScanAction()
    }
}