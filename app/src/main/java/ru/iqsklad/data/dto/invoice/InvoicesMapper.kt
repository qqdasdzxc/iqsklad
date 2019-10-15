package ru.iqsklad.data.dto.invoice

import ru.iqsklad.data.dto.equipment.EquipmentType

object InvoicesMapper {

    fun map(invoicesWithEquipment: InvoicesWithEquipment) {
        invoicesWithEquipment.invoices?.forEach { invoice ->
            invoice.statusTitle =
                findStatusTitle(invoicesWithEquipment.statuses, invoice.status)

            invoice.equipmentList.forEach { equipment ->
                equipment.equipmentTitle =
                    findEquipmentTitle(invoicesWithEquipment.equipmentTypes, equipment.equipmentID)
            }
        }
    }

    private fun findStatusTitle(
        statuses: List<InvoiceStatus>,
        invoiceStatusIDString: String
    ): String? = statuses.firstOrNull { status -> status.id.toString() == invoiceStatusIDString }?.title

    private fun findEquipmentTitle(
        equipmentTypes: List<EquipmentType>,
        equipmentID: String
    ): String? = equipmentTypes.firstOrNull { type -> type.equipmentID == equipmentID }?.title
}