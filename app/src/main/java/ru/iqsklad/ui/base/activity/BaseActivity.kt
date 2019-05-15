package ru.iqsklad.ui.base.activity

import android.annotation.SuppressLint
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.NavHostFragment
import ru.iqsklad.domain.manager.keyboard.KeyboardManager
import ru.iqsklad.domain.manager.keyboard.KeyboardStatus
import ru.iqsklad.ui.base.fragment.BaseFragment

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private var keyboardManager: KeyboardManager? = null

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == 139 || keyCode == 280) {
            ((supportFragmentManager
                .fragments
                .first() as NavHostFragment)
                .childFragmentManager
                .fragments
                .last() as BaseFragment<*>).handleScanPressButton()
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (((supportFragmentManager.fragments.first() as NavHostFragment)
                .childFragmentManager
                .fragments
                .last() as BaseFragment<*>).handleBackPress()
        ) {
            super.onBackPressed()
        }
    }

    fun getKeyboardStateListener(): LiveData<KeyboardStatus> {
        keyboardManager = KeyboardManager(this)
        return keyboardManager!!.getStateLiveData()
    }

    fun releaseKeyboardManager() {
        keyboardManager?.release()
    }
}