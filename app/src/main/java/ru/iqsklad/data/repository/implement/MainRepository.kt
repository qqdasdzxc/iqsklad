package ru.iqsklad.data.repository.implement

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import ru.dtk.lib.network.*
import ru.dtk.lib.network.builder.DtkNetBuilder
import ru.iqsklad.data.db.dao.MainDao
import ru.iqsklad.data.dto.procedure.Invoice
import ru.iqsklad.data.dto.user.UserType
import ru.iqsklad.data.dto.user.UsersWithRoles
import ru.iqsklad.data.repository.contract.IMainRepository
import ru.iqsklad.data.web.api.MainApi
import ru.iqsklad.data.web.factory.RequestBuilder
import ru.iqsklad.data.web.response.UsersResponse
import ru.iqsklad.data.web.response.api.EmptyResponse
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
) : IMainRepository {

    override fun getUsersWithChanges(
        type: UserType,
        searchString: String
    ): LiveData<DtkApiModel<UsersResponse>> {
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
                        .setParams(
                            mapOf(
                                Pair("date", getCurrentDate())
                            )
                        )
                        .build()
                )
            }
            .toLiveData()
    }

    override fun getInvoice(invoiceID: Int): LiveData<Invoice> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun loadInvoices() {
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
//    }

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

    override fun refreshEquipments() {

    }

    override fun loadAllData(): LiveData<DtkApiModel<EmptyResponse>> {
        return flow {
            coroutineScope {
                emit(LoadingDtkApiModel)

                try {
                    runBlocking {
                        val usersResponse = api.getUsersAsync(
                            requestBuilder
                                .createRequest()
                                .setMethod("person.getList")
                                .setParams(mapOf())
                                .build()
                        ).await()
                        dao.saveUsers(usersResponse.data!!.users)

                        val invoicesResponse = api.getInvoicesAsync(
                            requestBuilder
                                .createRequest()
                                .setMethod("invoice.getList")
                                .setParams(mapOf())
                                .build()
                        ).await()
                        //todo dao.invoices

                        val equipmentResponse = api.getEquipmentsAsync(
                            requestBuilder
                                .createRequest()
                                .setMethod("rfid.getList")
                                .setParams(mapOf())
                                .build()
                        ).await()
                        dao.saveEquipment(equipmentResponse.data!!.rfidList)
                    }
                    emit(SuccessDtkApiModel(EmptyResponse()))
                } catch (exception: Exception) {
                    emit(ErrorDtkApiModel(exception))
                }
            }
        }.toLiveData()
    }

    override fun loadAllChanges(lastUpdated: String): LiveData<DtkApiModel<EmptyResponse>> {
        return flow {
            coroutineScope {
                emit(LoadingDtkApiModel)

                try {
                    runBlocking {
                        val usersResponse = api.getUsersChangesAsync(
                            requestBuilder
                                .createRequest()
                                .setMethod("person.getChange")
                                .setParams(mapOf(
                                    Pair("last_update", lastUpdated)
                                ))
                                .build()
                        ).await()
                        usersResponse.data!!.users?.let {
                            dao.saveUsers(it)
                        }

                        val invoicesResponse = api.getInvoicesChangesAsync(
                            requestBuilder
                                .createRequest()
                                .setMethod("invoice.getList")
                                .setParams(mapOf(
                                    Pair("date", lastUpdated),
                                    Pair("last_update", "true")
                                ))
                                .build()
                        ).await()
                        //todo dao.invoices

                        val equipmentResponse = api.getEquipmentsChangesAsync(
                            requestBuilder
                                .createRequest()
                                .setMethod("rfid.getChange")
                                .setParams(mapOf(
                                    Pair("last_update", lastUpdated)
                                ))
                                .build()
                        ).await()
                        dao.saveEquipment(equipmentResponse.data!!.rfidList)
                    }
                    emit(SuccessDtkApiModel(EmptyResponse()))
                } catch (exception: Exception) {
                    emit(ErrorDtkApiModel(exception))
                }
            }
        }.toLiveData()
    }
}