package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentConfirmStewardBinding

class ConfirmStewardFragment: BaseFragment<FragmentConfirmStewardBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_confirm_steward

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.user = ConfirmStewardFragmentArgs.fromBundle(arguments!!).user

        binding.confirmStewardActionBarView.setBackPressedAction { onBackPressed() }

        binding.confirmUserChangeView.setOnClickListener { navController.navigateUp() }

        binding.confirmUserAcceptView.setOnClickListener {
            confirmStewardAction()
        }
    }

    private fun confirmStewardAction() {
        navController.navigate(ConfirmStewardFragmentDirections.actionConfirmStewardToInvoiceScan())
    }

    override fun handleScanPressButton() {
        confirmStewardAction()
    }
}