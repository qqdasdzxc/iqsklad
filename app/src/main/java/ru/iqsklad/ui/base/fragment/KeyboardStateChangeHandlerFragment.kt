package ru.iqsklad.ui.base.fragment

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.iqsklad.domain.manager.keyboard.KeyboardManager
import ru.iqsklad.domain.manager.keyboard.KeyboardStatus

abstract class KeyboardStateChangeHandlerFragment<B : ViewDataBinding> : BaseFragment<B>() {

    private var keyboardManager: KeyboardManager? = null

    override fun onResume() {
        super.onResume()

        initKeyboardObserve()
    }

    override fun onPause() {
        super.onPause()

        releaseKeyboardObserve()
    }

    private fun initKeyboardObserve() {
        getKeyboardStateListener().observe(this, Observer { status ->
            when (status!!) {
                KeyboardStatus.OPEN -> onKeyboardOpen()
                KeyboardStatus.CLOSED -> onKeyboardHide()
            }
        })
    }

    private fun releaseKeyboardObserve() {
        releaseKeyboardManager()
    }

    private fun getKeyboardStateListener(): LiveData<KeyboardStatus> {
        keyboardManager = KeyboardManager(activity!!)
        return keyboardManager!!.getStateLiveData()
    }

    private fun releaseKeyboardManager() {
        keyboardManager?.release()
    }

    abstract fun onKeyboardOpen()

    abstract fun onKeyboardHide()
}