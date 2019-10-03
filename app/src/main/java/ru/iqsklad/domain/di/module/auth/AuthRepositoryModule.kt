package ru.iqsklad.domain.di.module.auth

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import ru.dtk.lib.network.builder.DtkNetBuilder
import ru.iqsklad.data.repository.contract.IForwardersRepository
import ru.iqsklad.data.repository.implement.ForwardersRepository
import ru.iqsklad.data.web.api.UsersApi
import ru.iqsklad.data.web.factory.RequestBuilder
import ru.iqsklad.domain.di.scope.AuthScope

@Module
class AuthRepositoryModule {

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @AuthScope
    @Provides
    fun getForwardersRepository(
        api: UsersApi,
        controller: DtkNetBuilder,
        requestBuilder: RequestBuilder
    ): IForwardersRepository = ForwardersRepository(api, controller, requestBuilder)
}