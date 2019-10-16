package ru.iqsklad.data.repository.implement

import androidx.lifecycle.LiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import ru.dtk.lib.network.*
import ru.dtk.lib.network.builder.DtkNetBuilder
import ru.iqsklad.data.Constants.LOAD_ALL_DATA_PARAM
import ru.iqsklad.data.Constants.LOAD_ALL_INVOICES_DATA_PARAM
import ru.iqsklad.data.db.dao.MainDao
import ru.iqsklad.data.dto.invoice.InvoicesMapper
import ru.iqsklad.data.dto.rfid.RfidsMapper
import ru.iqsklad.data.dto.user.UserType
import ru.iqsklad.data.dto.user.UsersMapper
import ru.iqsklad.data.dto.user.UsersWithRoles
import ru.iqsklad.data.exception.InvoiceNotFoundException
import ru.iqsklad.data.repository.contract.IMainRepository
import ru.iqsklad.data.web.api.MainApi
import ru.iqsklad.data.web.factory.RequestBuilder
import ru.iqsklad.data.web.response.InvoiceResponse
import ru.iqsklad.data.web.response.InvoicesWithEquipmentResponse
import ru.iqsklad.data.web.response.RfidListResponse
import ru.iqsklad.data.web.response.UsersResponse
import ru.iqsklad.data.web.response.api.EmptyResponse
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
        searchString: String,
        lastUpdated: String
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
            .saveData {
                it.data?.let { nonNullData ->
                    UsersMapper.map(nonNullData)
                    nonNullData.users?.let { nonNullUsers ->
                        dao.saveUsers(nonNullUsers)
                    }
                }
            }
            .execute {
                api.getUsersChangesAsync(
                    requestBuilder
                        .createRequest()
                        .setMethod("person.getList")
                        .setParams(
                            mapOf(
                                Pair("date", lastUpdated),
                                Pair("last_update", "true")
                            )
                        )
                        .build()
                )
            }
            .toLiveData()
    }

    override fun getInvoice(invoiceID: String): LiveData<DtkApiModel<InvoiceResponse>> {
        return flow {
            coroutineScope {
                emit(LoadingDtkApiModel)

                try {
                    val invoice = dao.getInvoice(invoiceID)
                    if (invoice == null) {
                        emit(ErrorDtkApiModel(InvoiceNotFoundException()))
                    } else {
                        emit(SuccessDtkApiModel(InvoiceResponse().apply { data = invoice }))
                    }
                } catch (exception: Exception) {
                    emit(ErrorDtkApiModel(exception))
                }
            }
        }.toLiveData()
    }

    override fun refreshInvoices(lastUpdated: String) {
        controller
            .createQuery<InvoicesWithEquipmentResponse>("refreshInvoices")
            .withCache()
            .loadingCondition { true }
            .saveData {
                it.data?.let { nonNullData ->
                    InvoicesMapper.map(nonNullData)
                    nonNullData.invoices?.let { nonNullInvoices ->
                        dao.saveInvoices(nonNullInvoices)
                    }
                }
            }
            .execute {
                api.getInvoicesAsync(
                    requestBuilder
                        .createRequest()
                        .setMethod("invoice.getList")
                        .setParams(mapOf(
                            Pair("date", lastUpdated),
                            Pair("last_update", "true")
                        ))
                        .build()
                )
            }
    }

    override fun refreshEquipments(lastUpdated: String) {
        controller
            .createQuery<RfidListResponse>("refreshEquipments")
            .withCache()
            .loadingCondition { true }
            .saveData {
                it.data?.let { nonNullData ->
                    RfidsMapper.map(nonNullData)
                    nonNullData.rfidList?.let { nonNullRfidList ->
                        dao.saveEquipment(nonNullRfidList)
                    }
                }
            }
            .execute {
                api.getEquipmentsChangesAsync(
                    requestBuilder
                        .createRequest()
                        .setMethod("rfid.getChange")
                        .setParams(mapOf(
                            Pair("date", lastUpdated),
                            Pair("last_update", "true")
                        ))
                        .build()
                )
            }
    }

    override fun loadAllData(): LiveData<DtkApiModel<EmptyResponse>> {
        return flow {
            coroutineScope {
                emit(LoadingDtkApiModel)

                try {
                    val usersResponse = api.getUsersAsync(
                        requestBuilder
                            .createRequest()
                            .setMethod("person.getList")
                            .setParams(mapOf(
                                LOAD_ALL_DATA_PARAM
                            ))
                            .build()
                    ).await()
                    usersResponse.data?.let { nonNullData ->
                        UsersMapper.map(nonNullData)
                        nonNullData.users?.let {
                            dao.saveUsers(it)
                        }
                    }

                    val invoicesResponse = api.getInvoicesAsync(
                        requestBuilder
                            .createRequest()
                            .setMethod("invoice.getList")
                            .setParams(mapOf(
                                LOAD_ALL_INVOICES_DATA_PARAM
                            ))
                            .build()
                    ).await()
                    invoicesResponse.data?.let { nonNullData ->
                        InvoicesMapper.map(nonNullData)
                        nonNullData.invoices?.let {
                            dao.saveInvoices(it)
                        }
                    }

                    val equipmentResponse = api.getEquipmentsAsync(
                        requestBuilder
                            .createRequest()
                            .setMethod("rfid.getList")
                            .setParams(mapOf(
                                LOAD_ALL_DATA_PARAM
                            ))
                            .build()
                    ).await()
                    equipmentResponse.data?.let { nonNullData ->
                        RfidsMapper.map(nonNullData)
                        nonNullData.rfidList?.let {
                            dao.saveEquipment(it)
                        }
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
                    val usersResponse = api.getUsersChangesAsync(
                        requestBuilder
                            .createRequest()
                            .setMethod("person.getChange")
                            .setParams(mapOf(
                                Pair("last_update", lastUpdated)
                            ))
                            .build()
                    ).await()
                    usersResponse.data?.let { nonNullData ->
                        UsersMapper.map(nonNullData)
                        nonNullData.users?.let {
                            dao.saveUsers(it)
                        }
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
                    invoicesResponse.data?.let { nonNullData ->
                        InvoicesMapper.map(nonNullData)
                        nonNullData.invoices?.let {
                            dao.saveInvoices(it)
                        }
                    }

                    val equipmentResponse = api.getEquipmentsChangesAsync(
                        requestBuilder
                            .createRequest()
                            .setMethod("rfid.getChange")
                            .setParams(mapOf(
                                Pair("last_update", lastUpdated)
                            ))
                            .build()
                    ).await()
                    equipmentResponse.data?.let { nonNullData ->
                        RfidsMapper.map(nonNullData)
                        nonNullData.rfidList?.let {
                            dao.saveEquipment(it)
                        }
                    }
                    emit(SuccessDtkApiModel(EmptyResponse()))
                } catch (exception: Exception) {
                    emit(ErrorDtkApiModel(exception))
                }
            }
        }.toLiveData()
    }
}