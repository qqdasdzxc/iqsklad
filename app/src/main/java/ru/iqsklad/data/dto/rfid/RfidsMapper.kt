package ru.iqsklad.data.dto.rfid

import ru.iqsklad.data.dto.equipment.EquipmentType

object RfidsMapper {

    fun map(rfidsWithTypes: RfidListWithTypes) {
        rfidsWithTypes.rfidList?.forEach { rfid ->
            rfid.equipmentTypeTitle = findEquipmentTitle(rfidsWithTypes.equipmentsTypesList, rfid.equipmentID)
        }
    }

    private fun findEquipmentTitle(
        equipmentsTypesList: List<EquipmentType>,
        equipmentID: String
    ): String? = equipmentsTypesList.firstOrNull { type -> type.equipmentID == equipmentID }?.title
}