package ru.iqsklad.data.dto.invoice

import com.google.gson.annotations.SerializedName
import ru.iqsklad.data.dto.equipment.EquipmentType
import ru.iqsklad.data.dto.procedure.Invoice

class InvoicesWithEquipment(
    @SerializedName("status")
    val statuses: List<InvoiceStatus>,
    @SerializedName("equipment")
    val equipmentTypes: List<EquipmentType>,
    @SerializedName("invoiceList")
    val invoices: List<Invoice>?
)