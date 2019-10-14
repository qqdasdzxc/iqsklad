package ru.iqsklad.data.repository.contract

import androidx.lifecycle.LiveData
import ru.dtk.lib.network.DtkApiModel
import ru.iqsklad.data.dto.procedure.Invoice
import ru.iqsklad.data.dto.user.UserType
import ru.iqsklad.data.web.response.UsersResponse
import ru.iqsklad.data.web.response.api.EmptyResponse

interface IMainRepository {

    //fun getAllUsers(): LiveData<DtkApiModel<UsersResponse>>

    fun getUsersWithChanges(type: UserType, searchString: String): LiveData<DtkApiModel<UsersResponse>>

    fun getInvoice(invoiceID: Int): LiveData<Invoice>

    fun refreshInvoices()

    fun refreshEquipments()

    fun loadAllChanges(lastUpdated: String): LiveData<DtkApiModel<EmptyResponse>>

    fun loadAllData(): LiveData<DtkApiModel<EmptyResponse>>
}