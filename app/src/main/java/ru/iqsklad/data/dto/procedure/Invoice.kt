package ru.iqsklad.data.dto.procedure

class Invoice {
    var invoiceID: String? = null
    var inventoryList = mutableListOf(
        Inventory(
            inventoryID = 1, title = "Плед эконом. класса (RFID)", plannedCount = 10, rfidList = listOf(
                "E2801170000002071423DD04",
                "E2801170000002071423DD01",
                "E2801170000002071423D40E",
                "E2801170000002071423DD05",
                "E2801170000002071423DD00",
                "E2801170000002071423D40F",
                "E2801170000002071423DD03",
                "E2801170000002071423DD06",
                "E2801170000002071423D40D",
                "E2801170000002071423DD02"
            )
        ),
        Inventory(
            inventoryID = 2, title = "Плед бизнес класса (RFID)", plannedCount = 10, rfidList = listOf(
                "E20000160712016912509B7B",
                "EA0000160715013320704035",
                "E2000016071200880640D963",
                "E200001607120072201047FC",
                "E2000016071200801298966B"
                //"E20000160712016915607A6C",
                //"E20000160712006918105D81",
                //"E2000016071200630630D68F",
                //"E2000016071200880540E007",
                //"E20000160715003621603B1A"
            )
        )
    )

    fun increaseScannedCountIfContains(rfid: String): Boolean {
        for (inventory in inventoryList) {
            if (inventory.rfidList.contains(rfid)) {
                inventory.scannedCount++
                return true
            }
        }

        return false
    }

    fun setInitState() {
        for (inventory in inventoryList) {
            inventory.scannedCount = 0
        }
    }
}