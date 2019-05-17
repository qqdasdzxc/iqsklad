package ru.iqsklad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.iqsklad.R
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.data.dto.procedure.ScanResult
import ru.iqsklad.data.dto.procedure.ScanResultType
import ru.iqsklad.databinding.ItemScanResultBinding
import ru.iqsklad.ui.base.adapter.RecyclerListAdapter

class ScanResultAdapter : RecyclerListAdapter<ScanResult, ScanResultAdapter.ScanResultViewHolder>() {

    private lateinit var procedureType: ProcedureType

    fun setProcedureType(procedureType: ProcedureType) {
        this.procedureType = procedureType
    }

    override fun onBindCustomViewHolder(holder: ScanResultViewHolder, position: Int) {
        holder.populate(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanResultViewHolder {
        val binding = DataBindingUtil.inflate<ItemScanResultBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_scan_result,
            parent,
            false
        )
        return ScanResultViewHolder(binding)
    }

    inner class ScanResultViewHolder(private var binding: ItemScanResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populate(scanResult: ScanResult) {
            binding.scanResultStatusImageView.setImageResource(getScanResultImage(scanResult))
            binding.scanResultTextView.text = buildScanResultInfoString(scanResult)
        }

        private fun buildScanResultInfoString(scanResult: ScanResult): String {
            when (procedureType) {
                ProcedureType.PASS -> {
                    return when (scanResult.scanResultType) {
                        ScanResultType.SUCCESS -> binding.root.context.getString(
                            R.string.scan_result_success_procedure_type_pass_text,
                            scanResult.rfid
                        )
                        ScanResultType.EXCLUDED -> binding.root.context.getString(
                            R.string.scan_result_excluded_procedure_type_pass_text,
                            scanResult.rfid,
                            scanResult.invoiceNumber
                        )
                    }
                }
                ProcedureType.RECEIVE -> {
                    return when (scanResult.scanResultType) {
                        ScanResultType.SUCCESS -> binding.root.context.getString(
                            R.string.scan_result_success_procedure_type_receive_text,
                            scanResult.rfid
                        )
                        ScanResultType.EXCLUDED -> binding.root.context.getString(
                            R.string.scan_result_excluded_procedure_type_receive_text,
                            scanResult.rfid,
                            scanResult.invoiceNumber
                        )
                    }
                }
            }
        }

        @DrawableRes
        private fun getScanResultImage(scanResult: ScanResult): Int {
            return when (scanResult.scanResultType) {
                ScanResultType.SUCCESS -> R.drawable.ic_status_success
                ScanResultType.EXCLUDED -> R.drawable.ic_status_undefined
            }
        }
    }
}