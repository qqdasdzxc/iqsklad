package ru.iqsklad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.iqsklad.R
import ru.iqsklad.data.dto.procedure.Inventory
import ru.iqsklad.data.dto.procedure.InventoryScanMode
import ru.iqsklad.ui.base.adapter.RecyclerListAdapter
import ru.iqsklad.databinding.ItemInventoryBinding

class InventoryAdapter : RecyclerListAdapter<Inventory, InventoryAdapter.InventoryViewHolder>() {

    private var inventoryScanMode: InventoryScanMode = InventoryScanMode.PREVIEW

    override fun onBindCustomViewHolder(holder: InventoryViewHolder, position: Int) {
        holder.populate(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val binding = DataBindingUtil.inflate<ItemInventoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_inventory,
            parent,
            false
        )
        return InventoryViewHolder(binding)
    }

    fun setScanMode(inventoryScanMode: InventoryScanMode) {
        this.inventoryScanMode = inventoryScanMode
        notifyDataSetChanged()
    }

    inner class InventoryViewHolder(private var binding: ItemInventoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun populate(inventory: Inventory) {
            binding.inventory = inventory
            binding.inventoryScanMode = inventoryScanMode

            //TODO scanned count color
        }
    }
}