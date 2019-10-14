package ru.iqsklad.ui.auth.activity

import android.os.Bundle
import ru.iqsklad.R
import ru.iqsklad.ui.base.activity.BaseActivity

class AuthActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth_view)
    }
}