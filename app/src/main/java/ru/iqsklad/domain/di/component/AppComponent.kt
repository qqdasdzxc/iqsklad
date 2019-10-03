package ru.iqsklad.domain.di.component

import dagger.Component
import ru.iqsklad.domain.di.module.AppModule
import ru.iqsklad.domain.di.module.ClientModule
import ru.iqsklad.domain.di.module.RequestModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, ClientModule::class, RequestModule::class]
)
interface AppComponent {

    fun plusAuth(): AuthComponent

    fun plusProcedure(): ProcedureComponent
}