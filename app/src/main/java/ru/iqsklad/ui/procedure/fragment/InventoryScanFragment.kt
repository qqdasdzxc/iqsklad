package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import ru.iqsklad.R
import ru.iqsklad.data.dto.procedure.EquipmentScanMode
import ru.iqsklad.databinding.FragmentInventoryScanBinding
import ru.iqsklad.presentation.implementation.procedure.InventoryScanViewModel
import ru.iqsklad.presentation.presenter.procedure.InventoryScanPresenter
import ru.iqsklad.ui.adapter.EquipmentAdapter
import ru.iqsklad.ui.adapter.ScanResultAdapter
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.ui.base.fragment.NeedToOverrideBackPressFragment

class InventoryScanFragment : BaseFragment<FragmentInventoryScanBinding>(), NeedToOverrideBackPressFragment {

    private lateinit var presenter: InventoryScanPresenter
    private var equipmentAdapter = EquipmentAdapter()
    private var scanResultAdapter = ScanResultAdapter()

    override fun getLayoutResId(): Int = R.layout.fragment_inventory_scan

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = getPresenter<InventoryScanViewModel>()
        binding.presenter = presenter

        initObservable()
    }

    override fun onPause() {
        super.onPause()

        presenter.stopScan()
    }

    private fun initObservable() {
        presenter.getErrorLiveData().observe(this, Observer {
            showMessage(it)
        })

        presenter.getInvoiceInventoryLiveData().observe(this, Observer {
            equipmentAdapter.clear()
            equipmentAdapter.addAll(it)
        })

        presenter.getEquipmentScanMode().addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                sender?.let {
                    equipmentAdapter.setScanMode((sender as ObservableField<*>).get() as EquipmentScanMode)
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
        binding.inventoryListView.adapter = equipmentAdapter
        binding.scanResultListView.adapter = scanResultAdapter

        binding.inventoryScanActionView.setOnClickListener {
            processScanAction()
        }

        binding.inventoryScanEndActionView.setOnClickListener {
            navController.navigate(InventoryScanFragmentDirections.actionInventoryScanToProcedureSuccess())
        }
    }

    private fun processScanAction() {
        when (presenter.getEquipmentScanMode().get()) {
            EquipmentScanMode.PREVIEW -> initScanObserve()
            EquipmentScanMode.SCANNING -> stopScanObserve()
            EquipmentScanMode.STOPPED -> resumeScanObserve()
        }
    }

    private fun initScanObserve() {
        presenter.startScan()?.observe(this, Observer {
            it?.let { scanResult ->
                equipmentAdapter.notifyDataSetChanged()
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

    override fun onBackPressed() {
        navController.navigate(InventoryScanFragmentDirections.actionInventoryScanToProcedureCancel(presenter.getProcedureType()))
    }
}