package ru.iqsklad.domain.di.module.auth

import dagger.Module
import dagger.Provides
import ru.iqsklad.data.repository.contract.IForwardersRepository
import ru.iqsklad.data.repository.implement.ForwardersRepository
import ru.iqsklad.domain.di.scope.AuthScope

@Module
class AuthRepositoryModule {

    @AuthScope
    @Provides
    fun getForwardersRepository(): IForwardersRepository = ForwardersRepository()
}