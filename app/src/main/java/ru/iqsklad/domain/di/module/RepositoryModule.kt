package ru.iqsklad.domain.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import ru.dtk.lib.network.builder.DtkNetBuilder
import ru.iqsklad.data.db.dao.MainDao
import ru.iqsklad.data.repository.contract.IMainRepository
import ru.iqsklad.data.repository.implement.MainRepository
import ru.iqsklad.data.web.api.MainApi
import ru.iqsklad.data.web.factory.RequestBuilder
import ru.iqsklad.domain.ProcedureResultsSender
import javax.inject.Singleton

@Module
class RepositoryModule {

    @FlowPreview
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun getMainRepository(
        api: MainApi,
        dao: MainDao,
        controller: DtkNetBuilder,
        requestBuilder: RequestBuilder,
        procedureResultsSender: ProcedureResultsSender
    ): IMainRepository = MainRepository(api, dao, controller, requestBuilder, procedureResultsSender)
}