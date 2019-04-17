package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.iqsklad.R
import ru.iqsklad.data.dto.procedure.InventoryScanMode
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.implementation.procedure.InventoryScanViewModel
import ru.iqsklad.presentation.presenter.procedure.InventoryScanPresenter
import ru.iqsklad.ui.adapter.InventoryAdapter
import ru.iqsklad.utils.extensions.injectViewModel
import ru.iqsklad.databinding.FragmentInventoryScanBinding
import ru.iqsklad.ui.adapter.ScanResultAdapter
import javax.inject.Inject

class InventoryScanFragment: BaseFragment<FragmentInventoryScanBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var presenter: InventoryScanPresenter
    private var inventoryAdapter = InventoryAdapter()
    private var scanResultAdapter = ScanResultAdapter()

    override fun getLayoutResId(): Int = R.layout.fragment_inventory_scan

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

    override fun onPause() {
        super.onPause()

        presenter.stopScan()
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

        scanResultAdapter.setProcedureType(presenter.getProcedureType())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.inventoryListView.adapter = inventoryAdapter
        binding.scanResultListView.adapter = scanResultAdapter

        binding.inventoryScanActionView.setOnClickListener {
            processScanAction()
        }

        binding.inventoryScanEndActionView.setOnClickListener {
            navController.navigate(InventoryScanFragmentDirections.actionInventoryScanToProcedureSuccess())
        }
    }

    private fun processScanAction() {
        when (presenter.getInventoryScanMode().get()) {
            InventoryScanMode.PREVIEW -> initScanObserve()
            InventoryScanMode.SCANNING -> stopScanObserve()
            InventoryScanMode.STOPPED -> resumeScanObserve()
        }
    }

    private fun initScanObserve() {
        presenter.startScan().observe(this, Observer {
            it?.let { scanResult ->
                inventoryAdapter.notifyDataSetChanged()
                scanResultAdapter.add(scanResult)
                binding.scanResultListView.scrollToPosition(scanResultAdapter.itemCount - 1)
            }
        })
    }

    private fun stopScanObserve() {
        presenter.stopScan()
    }

    private fun resumeScanObserve() {
        initScanObserve()
    }

    override fun handleScanPressButton() {
        processScanAction()
    }
}