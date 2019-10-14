package ru.iqsklad.domain.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.iqsklad.data.web.api.MainApi
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun getMainApi(retrofit: Retrofit): MainApi = retrofit.create()
}