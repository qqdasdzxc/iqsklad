package ru.iqsklad.domain.di.module.procedure

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.iqsklad.data.web.api.ChangesApi
import ru.iqsklad.data.web.api.UsersApi
import ru.iqsklad.domain.di.scope.ProcedureScope

@Module
class ProcedureApiModule {

    @ProcedureScope
    @Provides
    fun getUsersApi(retrofit: Retrofit): UsersApi = retrofit.create()

    @ProcedureScope
    @Provides
    fun getChangesApi(retrofit: Retrofit): ChangesApi = retrofit.create()
}