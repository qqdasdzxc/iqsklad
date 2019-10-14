package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import ru.iqsklad.R
import ru.iqsklad.databinding.FragmentProcedureSuccessBinding
import ru.iqsklad.presentation.implementation.procedure.ProcedureSuccessViewModel
import ru.iqsklad.presentation.presenter.procedure.ProcedureSuccessPresenter
import ru.iqsklad.ui.base.fragment.BaseFragment

class ProcedureSuccessFragment: BaseFragment<FragmentProcedureSuccessBinding>() {

    private lateinit var presenter: ProcedureSuccessPresenter

    override fun getLayoutResId(): Int = R.layout.fragment_procedure_success

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = getPresenter<ProcedureSuccessViewModel>()
        binding.presenter = presenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.procedureSuccessMainMenuAction.setOnClickListener {
            handleMainMenuAction()
        }
    }

    private fun handleMainMenuAction() {
        navController.navigate(ProcedureSuccessFragmentDirections.actionProcedureSuccessToChooseProcedure())
    }

    override fun handleScanPressButton() {
        handleMainMenuAction()
    }
}