package ru.iqsklad.data.repository.contract

import androidx.lifecycle.LiveData
import ru.dtk.lib.network.DtkApiModel
import ru.iqsklad.data.dto.equipment.RFID_EPC
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.data.dto.rfid.Rfid
import ru.iqsklad.data.dto.user.UserType
import ru.iqsklad.data.web.response.InvoiceResponse
import ru.iqsklad.data.web.response.UsersResponse
import ru.iqsklad.data.web.response.api.EmptyResponse

interface IMainRepository {

    //fun getAllUsers(): LiveData<DtkApiModel<UsersResponse>>

    fun getUsers(type: UserType, searchString: String, lastUpdated: String): LiveData<DtkApiModel<UsersResponse>>

    fun getInvoice(invoiceID: String): LiveData<DtkApiModel<InvoiceResponse>>

    fun refreshInvoices(lastUpdated: String)

    fun refreshEquipments(lastUpdated: String)

    fun loadAllChanges(lastUpdated: String): LiveData<DtkApiModel<EmptyResponse>>

    fun loadAllData(): LiveData<DtkApiModel<EmptyResponse>>

    fun getRfidFromDB(epc: RFID_EPC): Rfid?

    fun sendScanResults(procedureDataHolder: ProcedureDataHolder): LiveData<DtkApiModel<EmptyResponse>>
}