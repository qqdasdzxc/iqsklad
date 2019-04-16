package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentInvoiceScanBinding

class InvoiceScanFragment: BaseFragment<FragmentInvoiceScanBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_invoice_scan

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.invoiceScanErrorActionTitle.setOnClickListener {
            navController.navigate(InvoiceScanFragmentDirections.actionInvoiceScanToInvoiceNumberInput())
        }

        //TODO integrate sdk for invoice scan
        //TODO navigate to invoice plan
    }
}