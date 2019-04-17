package ru.iqsklad.ui.auth.activity

import android.os.Bundle
import ru.iqsklad.R
import ru.iqsklad.domain.App
import ru.iqsklad.ui.base.activity.BaseActivity

class AuthActivity: BaseActivity() {

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