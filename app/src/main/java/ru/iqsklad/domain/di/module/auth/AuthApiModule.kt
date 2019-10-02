package ru.iqsklad.domain.di.module.auth

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.iqsklad.data.web.api.UsersApi
import ru.iqsklad.domain.di.scope.AuthScope

@Module
class AuthApiModule {

    @AuthScope
    @Provides
    fun getUsersApi(retrofit: Retrofit): UsersApi = retrofit.create()
}