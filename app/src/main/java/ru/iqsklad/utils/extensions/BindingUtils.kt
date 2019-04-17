package ru.iqsklad.utils.extensions

import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import ru.iqsklad.R
import ru.iqsklad.data.dto.procedure.InventoryScanMode

@BindingAdapter(value = ["isError"])
fun TextInputLayout.updateError(isError: Boolean) {
    setHintTextAppearance(if (isError) R.style.ErrorHintAppearance else R.style.DefaultHintAppearance)
    if (isError) {
        requestFocus()
    }
}

@BindingAdapter(value = ["addTextWatcher"])
fun EditText.updateTextWatcher(listener: TextWatcher) {
    addTextChangedListener(listener)
}

@BindingAdapter(value = ["scanActionText"])
fun MaterialButton.updateText(inventoryScanMode: InventoryScanMode) {
    setText(when (inventoryScanMode) {
        InventoryScanMode.PREVIEW -> R.string.invoice_preview_start_scan_action_title
        InventoryScanMode.SCANNING -> R.string.invoice_preview_stop_scan_action_title
        InventoryScanMode.STOPPED -> R.string.invoice_preview_resume_scan_action_title
    })
}