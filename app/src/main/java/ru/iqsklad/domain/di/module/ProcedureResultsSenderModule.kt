package ru.iqsklad.domain.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.iqsklad.data.db.dao.MainDao
import ru.iqsklad.data.web.api.MainApi
import ru.iqsklad.data.web.factory.RequestBuilder
import ru.iqsklad.domain.ProcedureResultsSender
import javax.inject.Singleton

@Module
class ProcedureResultsSenderModule {

    @Provides
    @Singleton
    fun getProcedureResultsSender(
        api: MainApi,
        dao: MainDao,
        context: Context,
        requestBuilder: RequestBuilder
    ) = ProcedureResultsSender(api, dao, context, requestBuilder)
}