package ru.iqsklad.ui.base.adapter

import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class RecyclerListAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected var data: MutableList<T>

    constructor() {
        this.data = ArrayList()
    }

    constructor(data: MutableList<T>) {
        this.data = data
    }

    fun add(`object`: T): Boolean {
        val isModified = data.add(`object`)
        if (isModified) {
            notifyItemInserted(itemCount)
        }
        return isModified
    }

    fun clear() {
        val size = itemCount
        data.clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getItem(position: Int): T {
        return data[position]
    }

    fun getPosition(item: T): Int {
        return data.indexOf(item)
    }

    fun insert(index: Int, `object`: T) {
        data.add(index, `object`)
        notifyItemInserted(index)
    }

    fun insert(index: Int, `object`: List<T>): Boolean {
        val isModified = data.addAll(index, `object`)
        if (isModified) {
            notifyItemRangeInserted(index, `object`.size)
        }
        return isModified
    }

    @Suppress("UNCHECKED_CAST")
    fun remove(`object`: Any): Boolean {
        val position = getPosition(`object` as T)
        val isModified = data.remove(`object`)
        if (isModified) {
            notifyItemRemoved(position)
        }
        return isModified
    }

    fun sort(comparator: Comparator<in T>) {
        Collections.sort(data, comparator)
        notifyItemRangeChanged(0, itemCount)
    }

    open fun addAll(historiesStates: List<T>): Boolean {
        val positionStart = data.size + 1
        val isModified = data.addAll(historiesStates)
        if (isModified) {
            notifyItemRangeInserted(positionStart, historiesStates.size)
        }
        return isModified
    }

    fun update(`object`: T): Boolean {
        val position = getPosition(`object`)
        if (position >= 0) {
            data[position] = `object`
            notifyItemChanged(position)
            return true
        }
        return false
    }

    fun update(objects: List<T>): Boolean {
        var isModified = false
        for (`object` in objects) {
            val position = getPosition(`object`)
            if (position >= 0) {
                data[position] = `object`
                isModified = true
            }
        }
        if (isModified) {
            notifyDataSetChanged()
        }
        return isModified
    }

    operator fun contains(`object`: T): Boolean {
        return data.contains(`object`)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindCustomViewHolder(holder as VH, position)
    }

    protected abstract fun onBindCustomViewHolder(holder: VH, position: Int)

    fun addOrUpdateOldItem(item: T) {
        val itemPosition = getPosition(item)
        if (itemPosition >= 0) {
            data[itemPosition] = item
            notifyItemChanged(itemPosition)
        } else {
            data.add(itemCount, item)
            notifyItemInserted(itemCount)
        }
    }

    fun addOrUpdateOldItems(items: List<T>) {
        for (item in items) {
            val itemPosition = getPosition(item)
            if (itemPosition >= 0) {
                data[itemPosition] = item
            } else {
                data.add(itemCount, item)
            }
        }
        notifyDataSetChanged()
    }

    fun addOrUpdateNewItems(items: List<T>) {
        for (i in items.indices.reversed()) {
            val item = items[i]
            val itemPosition = getPosition(item)
            if (itemPosition >= 0) {
                data[itemPosition] = item
            } else {
                data.add(0, item)
            }
        }
        notifyDataSetChanged()
    }

    fun addOrUpdateNewItem(item: T) {
        val itemPosition = getPosition(item)
        if (itemPosition >= 0) {
            data[itemPosition] = item
            notifyItemChanged(itemPosition)
        } else {
            data.add(0, item)
            notifyItemInserted(0)
        }
    }
}
