package ru.iqsklad.utils.extensions

import android.text.TextWatcher
import android.view.animation.Animation
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import ru.iqsklad.R
import ru.iqsklad.data.dto.dbstatus.DatabaseStatus
import ru.iqsklad.data.dto.procedure.EquipmentScanMode
import ru.iqsklad.data.dto.procedure.ProcedureType
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.dto.user.UserUI


@BindingAdapter("isError")
fun TextInputLayout.updateError(isError: Boolean) {
    setHintTextAppearance(if (isError) R.style.ErrorHintAppearance else R.style.DefaultHintAppearance)
    boxStrokeColor = ContextCompat.getColor(context, if (isError) android.R.color.holo_red_light else R.color.colorPrimary)

    if (isError) {
        requestFocus()
    }
}

@BindingAdapter("addTextWatcher")
fun EditText.updateTextWatcher(listener: TextWatcher) {
    addTextChangedListener(listener)
}

@BindingAdapter("scanActionTitle")
fun MaterialButton.updateScanActionTitle(equipmentScanMode: EquipmentScanMode) {
    setText(when (equipmentScanMode) {
        EquipmentScanMode.PREVIEW -> R.string.inventory_scan_start_action_title
        EquipmentScanMode.SCANNING -> R.string.inventory_scan_stop_action_title
        EquipmentScanMode.STOPPED -> R.string.inventory_scan_resume_action_title
    })
}

@BindingAdapter("userNameFirstLetter")
fun TextView.setUserNameFirstLetter(userUI: UserUI) {
    text = userUI.model.getFirstLetterString()
    if (userUI.showFirstLetter) {
        show()
    } else {
        hide()
    }
}

@BindingAdapter("userName")
fun TextView.setUserName(user: User) {
    text = user.getFullName()
}

@BindingAdapter("confirmUserIDText")
fun TextView.setConfirmUserLoginText(userID: String) {
    text = resources.getString(R.string.auth_confirm_user_id, userID)
}

@BindingAdapter("inventoryScanTitle")
fun TextView.setInventoryScanTitle(invoiceNumber: String?) {
    text = resources.getString(R.string.inventory_scan_title, invoiceNumber)
}

@BindingAdapter("inventoryScannedCountVisibility")
fun TextView.setInventoryScannedCountVisibility(equipmentScanMode: EquipmentScanMode) {
    if (equipmentScanMode == EquipmentScanMode.PREVIEW) hideAsGone() else show()
}

@BindingAdapter("inventoryScannedCountText")
fun TextView.setInventoryScannedCountText(scannedCount: Int) {
    text = scannedCount.toString()
}

@BindingAdapter("inventoryPlannedCountText")
fun TextView.setInventoryPlannedCountText(plannedCountString: String) {
    text = plannedCountString
}

@BindingAdapter("inventoryScanFactVisibility")
fun TextView.setInventoryScanFactVisibility(equipmentScanMode: EquipmentScanMode) {
    if (equipmentScanMode == EquipmentScanMode.PREVIEW) hideAsGone() else show()
}

@BindingAdapter("inventoryScanInfoLabelVisibility")
fun TextView.setInventoryScanInfoLabelVisibility(equipmentScanMode: EquipmentScanMode) {
    if (equipmentScanMode == EquipmentScanMode.PREVIEW) hideAsGone() else show()
}

@BindingAdapter("inventoryScanEndActionTitle")
fun MaterialButton.setInventoryScanEndActionTitle(procedureType: ProcedureType) {
    text = if (procedureType == ProcedureType.PASS) {
        resources.getString(R.string.inventory_scan_end_action_type_pass_title)
    } else {
        resources.getString(R.string.inventory_scan_end_action_type_receive_title)
    }
}

@BindingAdapter("inventoryScanEndActionVisibility")
fun MaterialButton.setInventoryScanEndActionVisibility(equipmentScanMode: EquipmentScanMode) {
    if (equipmentScanMode == EquipmentScanMode.STOPPED) show() else hideAsGone()
}

@BindingAdapter("procedureCancelTitle")
fun TextView.setProcedureCancelTitle(procedureType: ProcedureType) {
    text = if (procedureType == ProcedureType.PASS) {
        resources.getString(R.string.procedure_pass_cancel_title)
    } else {
        resources.getString(R.string.procedure_receive_cancel_title)
    }
}

@BindingAdapter("procedureCancelActionTitle")
fun MaterialButton.setProcedureCancelActionTitle(procedureType: ProcedureType) {
    text = if (procedureType == ProcedureType.PASS) {
        resources.getString(R.string.procedure_pass_cancel_action_confirm_title)
    } else {
        resources.getString(R.string.procedure_receive_cancel_action_confirm_title)
    }
}

@BindingAdapter("procedureResumeActionTitle")
fun MaterialButton.setProcedureResumeActionTitle(procedureType: ProcedureType) {
    text = if (procedureType == ProcedureType.PASS) {
        resources.getString(R.string.procedure_pass_cancel_action_resume_title)
    } else {
        resources.getString(R.string.procedure_receive_cancel_action_resume_title)
    }
}

@BindingAdapter("procedureSuccessTitle", "procedureSuccessInvoiceNumber")
fun TextView.setProcedureSuccessTitle(procedureType: ProcedureType, invoiceNumber: String) {
    text = if (procedureType == ProcedureType.PASS) {
        resources.getString(R.string.procedure_success_pass_type_text, invoiceNumber)
    } else {
        resources.getString(R.string.procedure_success_receive_type_text, invoiceNumber)
    }
}

@BindingAdapter("srcDatabaseStatus")
fun ImageView.setSrcDbStatus(databaseStatus: DatabaseStatus) {
    setImageResource(databaseStatus.imageResourceID)

    when (databaseStatus) {
        DatabaseStatus.NotUpdated, DatabaseStatus.Updated -> {
            clearAnimation()
        }
        DatabaseStatus.Updating -> {
            startAnimation(RotateAnimation(
                0f, 360f,
                RELATIVE_TO_SELF, 0.5f,
                RELATIVE_TO_SELF, 0.5f
            ).apply {
                duration = 2000
                repeatCount = Animation.INFINITE
            })
        }
    }
}

@BindingAdapter("textDatabaseStatus")
fun TextView.setTextDbStatus(databaseStatus: DatabaseStatus) {
    text = resources.getString(databaseStatus.stringID)
}