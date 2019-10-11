package ru.iqsklad.domain.di.module.splash

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.iqsklad.data.web.api.ChangesApi
import ru.iqsklad.domain.di.scope.SplashScope

@Module
class ChangeApiModule {

    @SplashScope
    @Provides
    fun getChangesApi(retrofit: Retrofit): ChangesApi = retrofit.create()
}