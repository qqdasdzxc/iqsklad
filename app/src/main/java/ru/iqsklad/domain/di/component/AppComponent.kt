package ru.iqsklad.domain.di.component

import dagger.Component
import ru.iqsklad.domain.di.module.AppModule
import ru.iqsklad.domain.di.module.ClientModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, ClientModule::class]
)
interface AppComponent {

    fun plusAuth(): AuthComponent

    fun plusProcedure(): ProcedureComponent
}