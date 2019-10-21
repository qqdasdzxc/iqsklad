package ru.iqsklad.domain.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.iqsklad.ui.splash.SplashActivity


class BootUpReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let {
            if (it == Intent.ACTION_BOOT_COMPLETED) {
                with(Intent(context, SplashActivity::class.java)) {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
            }
        }
    }
}