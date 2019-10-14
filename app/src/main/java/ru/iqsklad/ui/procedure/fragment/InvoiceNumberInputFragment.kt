package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.transition.TransitionManager
import ru.iqsklad.R
import ru.iqsklad.databinding.FragmentInvoiceNumberInputBinding
import ru.iqsklad.presentation.implementation.procedure.InvoiceNumberInputViewModel
import ru.iqsklad.presentation.presenter.procedure.InvoiceNumberInputPresenter
import ru.iqsklad.ui.base.fragment.KeyboardStateChangeHandlerFragment
import ru.iqsklad.utils.extensions.hideAsGone
import ru.iqsklad.utils.extensions.show

class InvoiceNumberInputFragment: KeyboardStateChangeHandlerFragment<FragmentInvoiceNumberInputBinding>() {

    private lateinit var presenter: InvoiceNumberInputPresenter

    override fun getLayoutResId(): Int = R.layout.fragment_invoice_number_input

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = getPresenter<InvoiceNumberInputViewModel>()
        binding.presenter = presenter

        initObservable()
    }

    private fun initObservable() {
        presenter.initAcceptInvoiceNumber()
        presenter.getAcceptInvoiceNumber().observe(this, Observer {
            navController.navigate(InvoiceNumberInputFragmentDirections.actionInvoiceNumberInputToInventoryScan())
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.invoiceNumberInputActionView.setOnClickListener {
            acceptInvoiceNumber()
        }

        binding.invoiceNumberInputScanActionView.setOnClickListener {
            onBackClicked()
        }

        binding.invoiceNumberInputScanEditView.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                TransitionManager.beginDelayedTransition(binding.rootView)
                binding.invoiceNumberInputActionView.hideAsGone()
            } else {
                if (binding.invoiceNumberInputActionView.visibility != View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(binding.rootView)
                    binding.invoiceNumberInputActionView.show()
                }
            }
        }
    }

    private fun acceptInvoiceNumber() {
        presenter.sendInvoiceNumber()
    }

    override fun onKeyboardOpen() {
        binding.invoiceNumberInputExampleImageView.hideAsGone()
    }

    override fun onKeyboardHide() {
        binding.invoiceNumberInputExampleImageView.show()
    }

    override fun handleScanPressButton() {
        acceptInvoiceNumber()
    }
}