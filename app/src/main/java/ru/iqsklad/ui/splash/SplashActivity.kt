package ru.iqsklad.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ru.iqsklad.R
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.ui.auth.activity.AuthActivity
import ru.iqsklad.ui.procedure.activity.ProcedureActivity
import ru.iqsklad.utils.pref.UserPreferences


class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_view)

        Handler().postDelayed({
            val userID = UserPreferences.getUserId(this)
            if (userID == null) {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, ProcedureActivity::class.java)
                intent.putExtra("user", User(id = "111111", lastName = "Кузьмин", firstName = "Дмитрий", middleName = "Игоревич", position = "1"))
                startActivity(intent)
                finish()
            }

        }, 2000)
    }
}