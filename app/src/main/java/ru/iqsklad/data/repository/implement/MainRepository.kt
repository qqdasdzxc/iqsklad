package ru.iqsklad.data.repository.implement

import androidx.lifecycle.LiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import ru.dtk.lib.network.DtkApiModel
import ru.dtk.lib.network.builder.DtkNetBuilder
import ru.dtk.lib.network.toLiveData
import ru.iqsklad.data.db.dao.MainDao
import ru.iqsklad.data.dto.procedure.Invoice
import ru.iqsklad.data.dto.user.UserType
import ru.iqsklad.data.dto.user.UsersWithRoles
import ru.iqsklad.data.repository.contract.IMainRepository
import ru.iqsklad.data.web.api.MainApi
import ru.iqsklad.data.web.factory.RequestBuilder
import ru.iqsklad.data.web.response.UsersResponse
import ru.iqsklad.utils.extensions.getCurrentDate
import javax.inject.Inject

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainRepository @Inject constructor(
    private var api: MainApi,
    private var dao: MainDao,
    private var controller: DtkNetBuilder,
    private var requestBuilder: RequestBuilder
): IMainRepository {

    override fun getUsers(type: UserType): LiveData<DtkApiModel<UsersResponse>> {
        return controller
            .createQuery<UsersResponse>("getUsers: $type")
            .withCache()
            .loadingCondition { it?.data == null }
            .loadData {
                val response = UsersResponse()
                val data = UsersWithRoles(emptyList(), emptyList())
                data.users = dao.getUsers(type.roleID)
                response.data = data
                response
            }
            .saveData { dao.saveUsers(it.data!!.users) }
            .execute {
                api.getUsersAsync(
                    requestBuilder
                        .createRequest()
                        .setMethod("person.getList")
                        .setParams(mapOf())
                        .build()
                )
            }
            .toLiveData()
    }

    override fun getUsersWithChanges(type: UserType, searchString: String): LiveData<DtkApiModel<UsersResponse>> {
        return controller
            .createQuery<UsersResponse>("getUsers: $type")
            .withCache()
            .loadingCondition { it?.data == null }
            .loadData {
                val response = UsersResponse()
                val data = UsersWithRoles(emptyList(), emptyList())
                data.users = dao.getUsers(type.roleID, searchString)
                response.data = data
                response
            }
            .saveData { dao.saveUsers(it.data!!.users) }
            .execute {
                api.getUsersChangesAsync(
                    requestBuilder
                        .createRequest()
                        .setMethod("person.getList")
                        .setParams(mapOf(
                            Pair("date", getCurrentDate())
                        ))
                        .build()
                )
            }
            .toLiveData()
    }

    override fun getInvoice(invoiceID: Int): LiveData<Invoice> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadInvoices() {
//        controller
//            .createQuery<InvoicesWithEquipmentResponse>("getInvoices")
//            .withCache()
//            .loadingCondition { true }
//            .saveData { dao.saveInvoices(it.data!!.invoices) }
//            .execute {
//                api.getInvoicesAsync(
//                    requestBuilder
//                        .createRequest()
//                        .setMethod("invoice.getList")
//                        .setParams(mapOf())
//                        .build()
//                )
//            }
    }

    override fun refreshInvoices() {
//        controller
//            .createQuery<InvoicesWithEquipmentResponse>("refreshInvoice")
//            .withCache()
//            .loadingCondition { true }
//            .saveData { dao.saveInvoices(it.data!!.invoices) }
//            .execute {
//                api.getInvoicesAsync(
//                    requestBuilder
//                        .createRequest()
//                        .setMethod("invoice.getList")
//                        .setParams(mapOf(
//                            Pair("last_update", "true")
//                        ))
//                        .build()
//                )
//            }
    }

    override fun loadEquipments() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshEquipments() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllChangesAsync(lastUpdated: String): LiveData<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}