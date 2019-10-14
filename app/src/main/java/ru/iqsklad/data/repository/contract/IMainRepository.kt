package ru.iqsklad.data.repository.contract

import androidx.lifecycle.LiveData
import ru.dtk.lib.network.DtkApiModel
import ru.iqsklad.data.dto.procedure.Invoice
import ru.iqsklad.data.dto.user.UserType
import ru.iqsklad.data.web.response.UsersResponse

interface IMainRepository {

    fun getUsers(type: UserType): LiveData<DtkApiModel<UsersResponse>>

    fun getUsersWithChanges(type: UserType, searchString: String): LiveData<DtkApiModel<UsersResponse>>

    fun getInvoice(invoiceID: Int): LiveData<Invoice>

    fun loadInvoices()

    fun refreshInvoices()

    fun loadEquipments()

    fun refreshEquipments()

    fun getAllChangesAsync(lastUpdated: String): LiveData<Boolean>
}