package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionManager
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentInvoiceNumberInputBinding
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.implementation.procedure.InvoiceNumberInputViewModel
import ru.iqsklad.presentation.presenter.procedure.InvoiceNumberInputPresenter
import ru.iqsklad.ui.base.fragment.KeyboardStateChangeHandlerFragment
import ru.iqsklad.utils.extensions.hideAsGone
import ru.iqsklad.utils.extensions.injectViewModel
import ru.iqsklad.utils.extensions.show
import javax.inject.Inject

class InvoiceNumberInputFragment: KeyboardStateChangeHandlerFragment<FragmentInvoiceNumberInputBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var presenter: InvoiceNumberInputPresenter

    override fun getLayoutResId(): Int = R.layout.fragment_invoice_number_input

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.procedureComponent?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = injectViewModel<InvoiceNumberInputViewModel>(viewModelFactory)
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