package ru.iqsklad.domain.di.module

import dagger.Module
import dagger.Provides
import ru.iqsklad.data.web.factory.RequestBuilder
import javax.inject.Singleton

@Module
class RequestModule {

    @Singleton
    @Provides
    fun getRequestFactory() = RequestBuilder()
}