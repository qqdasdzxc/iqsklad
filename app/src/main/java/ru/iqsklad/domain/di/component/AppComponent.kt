package ru.iqsklad.domain.di.component

import dagger.Component
import ru.iqsklad.domain.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    fun plus(): AuthComponent
}