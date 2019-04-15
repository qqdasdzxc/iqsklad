package ru.iqsklad.utils.extensions

import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import ru.iqsklad.R

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