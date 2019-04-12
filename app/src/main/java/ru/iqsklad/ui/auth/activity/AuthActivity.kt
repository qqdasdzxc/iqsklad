package ru.iqsklad.ui.auth.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.iqsklad.R
import ru.iqsklad.domain.App

class AuthActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).buildActivationComponent()
        setContentView(R.layout.activity_auth_view)
    }

    override fun onDestroy() {
        super.onDestroy()

        App.authComponent = null
    }
}