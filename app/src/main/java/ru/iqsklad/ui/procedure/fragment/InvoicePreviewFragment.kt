package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentInvoicePreviewBinding
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.implementation.procedure.InvoicePreviewViewModel
import ru.iqsklad.presentation.presenter.procedure.InvoicePreviewPresenter
import ru.iqsklad.ui.adapter.InventoryAdapter
import ru.iqsklad.utils.extensions.injectViewModel
import javax.inject.Inject

class InvoicePreviewFragment: BaseFragment<FragmentInvoicePreviewBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var presenter: InvoicePreviewPresenter
    private var inventoryAdapter = InventoryAdapter()

    override fun getLayoutResId(): Int = R.layout.fragment_invoice_preview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.procedureComponent?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = injectViewModel<InvoicePreviewViewModel>(viewModelFactory)
        binding.presenter = presenter

        initObservable()
    }

    private fun initObservable() {
        presenter.getInvoiceInventoryLiveData().observe(this, Observer {
            inventoryAdapter.clear()
            inventoryAdapter.addAll(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.invoicePreviewListView.adapter = inventoryAdapter

        binding.invoicePreviewStartScanActionView.setOnClickListener {

        }
    }
}