package ru.iqsklad.ui.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import ru.iqsklad.ui.base.activity.BaseActivity

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    lateinit var binding: B
    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navController = Navigation.findNavController(binding.root)
    }

    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected inline fun <reified T : ViewModel> getPresenter(): T {
        return ViewModelProviders.of(this)[T::class.java]
    }

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

    open fun onBackPressed() {
        (activity as BaseActivity).onBackPressed()
    }

    abstract fun handleScanPressButton()
}