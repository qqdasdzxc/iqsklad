package ru.iqsklad.data.dto.changes

import com.google.gson.annotations.SerializedName
import ru.iqsklad.data.dto.invoice.InvoicesWithEquipment
import ru.iqsklad.data.dto.rfid.RfidList
import ru.iqsklad.data.dto.user.UsersWithRoles

class AllDataChanges(
    @SerializedName("person")
    val personChanges: UsersWithRoles,
    @SerializedName("invoice")
    val invoiceChanges: InvoicesWithEquipment,
    @SerializedName("rfid")
    val rfidChanges: RfidList
)