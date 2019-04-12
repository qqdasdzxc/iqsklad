package ru.iqsklad.domain.di.module.auth

import dagger.Module
import dagger.Provides
import ru.iqsklad.data.repository.contract.IUsersRepository
import ru.iqsklad.data.repository.implement.UsersRepository
import ru.iqsklad.domain.di.scope.AuthScope
import javax.inject.Singleton

@Module
class AuthRepositoryModule {

    @AuthScope
    @Provides
    fun getUsersRepository(): IUsersRepository = UsersRepository()
}