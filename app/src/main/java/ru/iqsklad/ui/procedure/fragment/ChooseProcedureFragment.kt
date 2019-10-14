package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import ru.iqsklad.R
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.databinding.FragmentChooseProcedureBinding
import ru.iqsklad.presentation.implementation.procedure.ChooseProcedureViewModel
import ru.iqsklad.presentation.presenter.procedure.ChooseProcedurePresenter
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.ui.procedure.activity.ProcedureActivityArgs

class ChooseProcedureFragment: BaseFragment<FragmentChooseProcedureBinding>() {

    private lateinit var presenter: ChooseProcedurePresenter

    override fun getLayoutResId(): Int = R.layout.fragment_choose_procedure

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = getPresenter<ChooseProcedureViewModel>()
        binding.user = ProcedureActivityArgs.fromBundle(activity!!.intent.extras!!).user
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.procedurePassView.setOnClickListener {
            setProcedureTypeAndNavigateToChooseSteward(ProcedureType.PASS)
        }
        binding.procedureReceiveView.setOnClickListener {
            setProcedureTypeAndNavigateToChooseSteward(ProcedureType.RECEIVE)
        }
    }

    private fun setProcedureTypeAndNavigateToChooseSteward(procedureType: ProcedureType) {
        presenter.setProcedureType(procedureType)
        navController.navigate(ChooseProcedureFragmentDirections.actionChooseProcedureToChooseSteward())
    }

    override fun handleScanPressButton() {
        showMessage(R.string.choose_procedure_message)
    }
}