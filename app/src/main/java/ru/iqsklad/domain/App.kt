package ru.iqsklad.domain

import android.app.Application
import ru.iqsklad.domain.di.component.AppComponent
import ru.iqsklad.domain.di.component.AuthComponent
import ru.iqsklad.domain.di.component.DaggerAppComponent
import ru.iqsklad.domain.di.component.ProcedureComponent
import ru.iqsklad.domain.di.module.AppModule

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
        var authComponent: AuthComponent? = null
        var procedureComponent: ProcedureComponent? = null
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    fun buildActivationComponent(): AuthComponent {
        authComponent = appComponent.plusAuth()
        return authComponent!!
    }

    fun buildProcedureComponent(): ProcedureComponent {
        procedureComponent = appComponent.plusProcedure()
        return procedureComponent!!
    }

}