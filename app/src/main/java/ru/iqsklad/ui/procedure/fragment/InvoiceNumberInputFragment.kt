package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.transition.TransitionManager
import ru.iqsklad.R
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.LoadingUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.databinding.FragmentInvoiceNumberInputBinding
import ru.iqsklad.presentation.implementation.procedure.FindInvoiceViewModel
import ru.iqsklad.presentation.presenter.procedure.FindInvoicePresenter
import ru.iqsklad.ui.base.fragment.KeyboardStateChangeHandlerFragment
import ru.iqsklad.utils.extensions.hideAsGone
import ru.iqsklad.utils.extensions.show

class InvoiceNumberInputFragment: KeyboardStateChangeHandlerFragment<FragmentInvoiceNumberInputBinding>() {

    private lateinit var presenter: FindInvoicePresenter

    override fun getLayoutResId(): Int = R.layout.fragment_invoice_number_input

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = getPresenter<FindInvoiceViewModel>().apply {
            getFindingInvoiceResult().observe(this@InvoiceNumberInputFragment, Observer { uiModel ->
                when (uiModel) {
                    LoadingUiModel -> showMessage("Поиск накладной в базе...")
                    is SuccessUiModel -> {
                        if (uiModel.data == null) {
                            showMessage("Накладная не найдена! Попробуйте еще раз")
                        } else {
                            setProcedureInvoice(uiModel.data)
                            navigateToInventoryScan()
                        }
                    }
                    is ErrorUiModel -> showMessage(uiModel.error)
                }
            })
        }
    }

    private fun navigateToInventoryScan() {
        navController.navigate(InvoiceNumberInputFragmentDirections.actionInvoiceNumberInputToInventoryScan())
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
        presenter.findInvoice(binding.invoiceNumberInputScanEditView.text.toString())
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