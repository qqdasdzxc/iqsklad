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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import ru.iqsklad.R
import ru.iqsklad.ui.base.activity.BaseActivity
import ru.iqsklad.ui.base.bottom.StatusFragment
import ru.iqsklad.ui.base.view.ActionBarView
import ru.iqsklad.utils.pref.UserPreferences

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

    override fun onCallClicked() {}

    override fun onExitClicked() {
        UserPreferences.removeUser(activity!!)
        navController.navigate(R.id.auth_activity)
        activity?.finish()
    }

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

    fun showMessage(message: String?) {
        Snackbar.make(binding.root, message ?: getString(R.string.error_network_connected), Snackbar.LENGTH_LONG).show()
    }

    fun hideKeyBoard() {
        activity?.let {
            val manager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(it.findViewById<ViewGroup>(android.R.id.content).windowToken, 0)
        }
    }

    fun showKeyBoard() {
        activity?.let {
            val manager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    private fun showStatusDialog() {
        statusFragment.show(activity!!.supportFragmentManager)
    }

    abstract fun handleScanPressButton()
}