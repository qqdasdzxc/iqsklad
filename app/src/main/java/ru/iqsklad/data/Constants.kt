package ru.iqsklad.data

import ru.dtk.lib.extensions.removeDays
import ru.iqsklad.utils.extensions.getLastUpdatedTime
import java.util.*

object Constants {

    const val SERVER_URL_API_DEBUG = "https://test.intra.s7.aero/jsonrpc/"
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm"
    const val TIMEOUT_REQUEST = 120L
    const val MILLISECONDS_EXPIRED = 28800000

    val LOAD_ALL_DATA_PARAM = Pair("date", "1970")
    val LOAD_ALL_INVOICES_DATA_PARAM = Pair("date", Calendar.getInstance().apply {
        removeDays(3)
        //КОММЕНТАРИЙ РАЗРАБОТЧКА S7
//        Чтобы выгрузить накладные при первом запуске приложения достаточно запустить
//        {"jsonrpc": "2.0", "method": "invoice.getList", "id": "1"}.
//        В этом случае придут все накладные за текущий день и 2 передыдущих,
//        этих данных будет достаточно.
    }.getLastUpdatedTime())

//тестовые метки
//  "E2801170000002071423DD04",
//  "E2801170000002071423DD01",
//  "E2801170000002071423D40E",
//  "E2801170000002071423DD05",
//  "E2801170000002071423DD00",
//  "E2801170000002071423D40F",
//  "E2801170000002071423DD03",
//  "E2801170000002071423DD06",
//  "E2801170000002071423D40D",
//  "E2801170000002071423DD02"

//  "E20000160712016912509B7B",
//  "EA0000160715013320704035",
//  "E2000016071200880640D963",
//  "E200001607120072201047FC",
//  "E2000016071200801298966B"
//  "E20000160712016915607A6C",
//  "E20000160712006918105D81",
//  "E2000016071200630630D68F",
//  "E2000016071200880540E007",
//  "E20000160715003621603B1A"
}