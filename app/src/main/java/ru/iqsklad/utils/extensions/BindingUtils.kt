package ru.iqsklad.utils.extensions

import android.text.TextWatcher
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import ru.iqsklad.R
import ru.iqsklad.data.dto.procedure.InventoryScanMode

@BindingAdapter(value = ["isError"])
fun TextInputLayout.updateError(isError: Boolean) {
    setHintTextAppearance(if (isError) R.style.ErrorHintAppearance else R.style.DefaultHintAppearance)
    boxStrokeColor = ContextCompat.getColor(context, if (isError) android.R.color.holo_red_light else R.color.colorPrimary)

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
        InventoryScanMode.PREVIEW -> R.string.inventory_scan_start_action_title
        InventoryScanMode.SCANNING -> R.string.inventory_scan_stop_action_title
        InventoryScanMode.STOPPED -> R.string.inventory_scan_resume_action_title
    })
}