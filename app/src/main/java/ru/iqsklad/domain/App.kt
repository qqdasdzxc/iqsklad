package ru.iqsklad.domain

import android.app.Application
import ru.iqsklad.R
import ru.iqsklad.domain.di.component.AppComponent
import ru.iqsklad.domain.di.component.AuthComponent
import ru.iqsklad.domain.di.component.DaggerAppComponent
import ru.iqsklad.domain.di.component.ProcedureComponent
import ru.iqsklad.domain.di.module.AppModule
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
        var authComponent: AuthComponent? = null
        var procedureComponent: ProcedureComponent? = null
    }

    override fun onCreate() {
        super.onCreate()

        initCalligraphy()
        initDagger()
    }

    private fun initCalligraphy() {
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
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