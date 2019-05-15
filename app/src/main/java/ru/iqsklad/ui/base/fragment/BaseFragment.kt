package ru.iqsklad.ui.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import ru.iqsklad.R
import ru.iqsklad.domain.manager.keyboard.KeyboardStatus
import ru.iqsklad.ui.base.activity.BaseActivity
import ru.iqsklad.ui.base.view.ActionBarView
import ru.iqsklad.ui.procedure.fragment.bottom.StatusFragment

abstract class BaseFragment<B : ViewDataBinding> : Fragment(), ActionBarView.ActionBarClickListener {

    lateinit var binding: B
    lateinit var navController: NavController

    private var statusFragment = StatusFragment.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navController = Navigation.findNavController(binding.root)
    }

    override fun onResume() {
        super.onResume()

        if (this is KeyboardStateChangeListenerFragment) {
            initKeyboardObserve()
        }
    }

    override fun onPause() {
        super.onPause()

        if (this is KeyboardStateChangeListenerFragment) {
            releaseKeyboardObserve()
        }
    }

    private fun initKeyboardObserve() {
        (activity as BaseActivity).getKeyboardStateListener().observe(this, Observer { status ->
            (this as KeyboardStateChangeListenerFragment).let {
                when (status!!) {
                    KeyboardStatus.OPEN -> it.onKeyboardOpen()
                    KeyboardStatus.CLOSED -> it.onKeyboardHide()
                }
            }
        })
    }

    private fun releaseKeyboardObserve() {
        if (this is KeyboardStateChangeListenerFragment) {
            (activity as BaseActivity).releaseKeyboardManager()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBarClickActionsIfExists()
    }

    private fun setActionBarClickActionsIfExists() {
        findActionBarOrNull()?.setActionClickListener(this)
    }

    override fun onBackClicked() = (activity as BaseActivity).onBackPressed()

    fun handleBackPress(): Boolean {
        findActionBarOrNull()?.let {
            if (it.isInSearchMode()) {
                it.exitFromSearchMode()
                return false
            }
        }

        if (this is NeedToOverrideBackPressFragment) {
            (this as NeedToOverrideBackPressFragment).onBackPressed()
            return false
        }

        return true
    }

    override fun onStatusClicked() = showStatusDialog()

    override fun onHelpClicked() = navController.navigate(R.id.help_fragment)

    override fun onSearchClicked() = showKeyBoard()

    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected inline fun <reified T : ViewModel> getPresenter(): T {
        return ViewModelProviders.of(this)[T::class.java]
    }

    private fun findActionBarOrNull(): ActionBarView? = (view as ViewGroup).children.firstOrNull { it is ActionBarView } as ActionBarView

    fun showMessage(@StringRes messageResId: Int) {
        showMessage(getString(messageResId))
    }

    fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    fun hideKeyBoard() {
        activity?.let {
            val manager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }

    fun showKeyBoard() {
        activity?.let {
            val manager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun showStatusDialog() {
        statusFragment.show(activity!!.supportFragmentManager)
    }

    abstract fun handleScanPressButton()
}