package ru.iqsklad.ui.procedure.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import ru.iqsklad.R
import ru.iqsklad.ui.auth.activity.AuthActivity
import ru.iqsklad.ui.base.activity.BaseActivity
import ru.iqsklad.utils.pref.UserPreferences
import java.lang.ref.WeakReference


class ProcedureActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_procedure)
        UserInteractionController.create(this, Handler())
    }

    override fun onUserInteraction() {
        super.onUserInteraction()

        UserInteractionController.reset()
    }

    private object UserInteractionController: Runnable {

        const val USER_INACTIVE_CAP = 3600000L //one hour

        var activityReference: WeakReference<Activity>? = null
        var handlerReference: WeakReference<Handler>? = null

        fun create(activity: Activity, handler: Handler) {
            activityReference = WeakReference(activity)
            handlerReference = WeakReference(handler)
            reset()
        }

        override fun run() {
            activityReference?.get()?.let { activity ->
                handlerReference?.get()?.removeCallbacks(this)
                UserPreferences.removeUser(activity)
                activity.startActivity(Intent(activity, AuthActivity::class.java))
                activity.finish()
            }
        }

        fun reset() = handlerReference?.get()?.let {
            it.removeCallbacks(this)
            it.postDelayed(this, USER_INACTIVE_CAP)
        }
    }
}
