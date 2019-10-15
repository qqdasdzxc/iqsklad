package ru.iqsklad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.iqsklad.R
import ru.iqsklad.data.dto.equipment.Equipment
import ru.iqsklad.data.dto.procedure.EquipmentScanMode
import ru.iqsklad.databinding.ItemInventoryBinding
import ru.iqsklad.ui.base.adapter.RecyclerListAdapter

class EquipmentAdapter : RecyclerListAdapter<Equipment, EquipmentAdapter.EquipmentViewHolder>() {

    private var equipmentScanMode: EquipmentScanMode = EquipmentScanMode.PREVIEW

    override fun onBindCustomViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.populate(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val binding = DataBindingUtil.inflate<ItemInventoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_inventory,
            parent,
            false
        )
        return EquipmentViewHolder(binding)
    }

    fun setScanMode(equipmentScanMode: EquipmentScanMode) {
        this.equipmentScanMode = equipmentScanMode
        notifyDataSetChanged()
    }

    inner class EquipmentViewHolder(private var binding: ItemInventoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun populate(equipment: Equipment) {
            binding.equipment = equipment
            binding.equipmentScanMode = equipmentScanMode

            updateScannedCountColor(equipment.scannedCount, equipment.count.toInt())
        }

        private fun updateScannedCountColor(scannedCount: Int, plannedCount: Int) {
            when {
                scannedCount < plannedCount -> {
                    binding.inventoryItemScannedCountLabel.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.inventory_scan_fact_status_fail_color)
                    )
                }
                else -> {
                    binding.inventoryItemScannedCountLabel.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.inventory_scan_fact_status_success_color)
                    )
                }
            }
        }
    }
}