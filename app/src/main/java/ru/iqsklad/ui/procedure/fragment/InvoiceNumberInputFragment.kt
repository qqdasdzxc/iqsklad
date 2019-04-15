package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentInvoiceNumberInputBinding
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.implementation.procedure.InvoiceNumberInputViewModel
import ru.iqsklad.presentation.presenter.procedure.InvoiceNumberInputPresenter
import ru.iqsklad.utils.extensions.injectViewModel
import javax.inject.Inject

class InvoiceNumberInputFragment: BaseFragment<FragmentInvoiceNumberInputBinding>() {

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
        presenter.getAcceptInvoiceNumber().observe(this, Observer {
            //TODO navigate to invoice plan
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.invoiceNumberInputActionView.setOnClickListener {
            presenter.sendInvoiceNumber()
        }
    }
}