package ru.iqsklad.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.iqsklad.R
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.LoadingUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.presentation.implementation.update.UpdateViewModel
import ru.iqsklad.presentation.presenter.update.UpdatePresenter
import ru.iqsklad.ui.auth.activity.AuthActivity
import ru.iqsklad.ui.procedure.activity.ProcedureActivity
import ru.iqsklad.utils.pref.UserPreferences

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_view)

        val presenter: UpdatePresenter = ViewModelProviders.of(this).get(UpdateViewModel::class.java)
        presenter.updateDB(this).observe(this, Observer { uiModel ->
            when (uiModel) {
                LoadingUiModel -> {
                }
                is SuccessUiModel -> {
                    checkLogin()
                }
                is ErrorUiModel -> {
                    if (uiModel.error == null) {
                        checkLogin()
                    } else {
                        Toast.makeText(this, uiModel.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun checkLogin() {
        val user = UserPreferences.getUser(this)
        if (user == null) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, ProcedureActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            finish()
        }
    }
}