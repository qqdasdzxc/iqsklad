package ru.iqsklad.ui.base.fragment

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.iqsklad.domain.source.keyboard.KeyboardStatusDataSource
import ru.iqsklad.domain.source.keyboard.KeyboardStatus

abstract class KeyboardStateChangeHandlerFragment<B : ViewDataBinding> : BaseFragment<B>() {

    private var keyboardStatusDataSource: KeyboardStatusDataSource? = null

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
        keyboardStatusDataSource = KeyboardStatusDataSource(activity!!)
        return keyboardStatusDataSource!!.getStateLiveData()
    }

    private fun releaseKeyboardManager() {
        keyboardStatusDataSource?.release()
    }

    abstract fun onKeyboardOpen()

    abstract fun onKeyboardHide()
}