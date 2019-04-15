package ru.iqsklad.presentation.observable

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ObservableField

class TextField {
    var observeWrapperEdit = ObservableField<Boolean>()
    var observeEdit = EditField()

    fun showError() {
        observeWrapperEdit.set(true)
    }

    fun isEmpty(): Boolean = observeEdit.get().isNullOrEmpty()

    inner class EditField : ObservableField<String>(), TextWatcher {

        override fun afterTextChanged(editable: Editable?) {
            set(if (!editable.isNullOrEmpty()) editable.toString() else null)
            observeWrapperEdit.set(false)
        }

        override fun beforeTextChanged(value: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}