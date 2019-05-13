package ru.iqsklad.ui.base.activity

import android.annotation.SuppressLint
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.ui.base.fragment.NeedToOverrideBackPressFragment

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

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
        if ((supportFragmentManager.fragments.first() as NavHostFragment)
                .childFragmentManager
                .fragments
                .last() is NeedToOverrideBackPressFragment
        ) {
            ((supportFragmentManager.fragments.first() as NavHostFragment)
                .childFragmentManager
                .fragments
                .last() as NeedToOverrideBackPressFragment).onBackPressed()
        } else {
            super.onBackPressed()
        }
    }
}