package ru.iqsklad.domain

import android.app.Application
import com.google.firebase.FirebaseApp
import ru.iqsklad.R
import ru.iqsklad.domain.di.component.AppComponent
import ru.iqsklad.domain.di.component.DaggerAppComponent
import ru.iqsklad.domain.di.module.AppModule
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initFirebase()
        initCalligraphy()
        initDagger()
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
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

}