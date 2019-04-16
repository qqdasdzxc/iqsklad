package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.iqsklad.R
import ru.iqsklad.data.dto.procedure.InventoryScanMode
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentInvoicePreviewBinding
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.implementation.procedure.InventoryScanViewModel
import ru.iqsklad.presentation.presenter.procedure.InventoryScanPresenter
import ru.iqsklad.ui.adapter.InventoryAdapter
import ru.iqsklad.utils.extensions.injectViewModel
import javax.inject.Inject

class InventoryScanFragment: BaseFragment<FragmentInvoicePreviewBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var presenter: InventoryScanPresenter
    private var inventoryAdapter = InventoryAdapter()

    override fun getLayoutResId(): Int = R.layout.fragment_invoice_preview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.procedureComponent?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = injectViewModel<InventoryScanViewModel>(viewModelFactory)
        binding.presenter = presenter

        initObservable()
    }

    private fun initObservable() {
        presenter.getInvoiceInventoryLiveData().observe(this, Observer {
            inventoryAdapter.clear()
            inventoryAdapter.addAll(it)
        })

        presenter.getInventoryScanMode().addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                sender?.let {
                    inventoryAdapter.setScanMode((sender as ObservableField<*>).get() as InventoryScanMode)
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.invoicePreviewListView.adapter = inventoryAdapter

        binding.invoicePreviewStartScanActionView.setOnClickListener {
            initScanObserve()
        }
    }

    private fun initScanObserve() {
        presenter.startScan().observe(this, Observer {
            //todo add rfid to info inventory recycler
            inventoryAdapter.notifyDataSetChanged()
        })
    }
}