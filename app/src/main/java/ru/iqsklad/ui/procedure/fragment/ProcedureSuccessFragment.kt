package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentProcedureSuccessBinding
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.implementation.procedure.ProcedureSuccessViewModel
import ru.iqsklad.presentation.presenter.procedure.ProcedureSuccessPresenter
import ru.iqsklad.utils.extensions.injectViewModel
import javax.inject.Inject

class ProcedureSuccessFragment: BaseFragment<FragmentProcedureSuccessBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var presenter: ProcedureSuccessPresenter

    override fun getLayoutResId(): Int = R.layout.fragment_procedure_success

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.procedureComponent?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = injectViewModel<ProcedureSuccessViewModel>(viewModelFactory)
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