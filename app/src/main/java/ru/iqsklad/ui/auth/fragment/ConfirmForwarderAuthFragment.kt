package ru.iqsklad.ui.auth.fragment

import android.os.Bundle
import android.view.View
import ru.iqsklad.R
import ru.iqsklad.databinding.FragmentAuthConfirmForwarderBinding
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.utils.pref.UserPreferences

class ConfirmForwarderAuthFragment : BaseFragment<FragmentAuthConfirmForwarderBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_auth_confirm_forwarder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = binding.apply {
        user = ConfirmForwarderAuthFragmentArgs.fromBundle(arguments!!).user
        confirmUserChangeView.setOnClickListener {
            navController.navigateUp()
        }
        confirmUserAcceptView.setOnClickListener {
            saveUserSession()
            confirmForwarderAction()
        }
    }

    private fun saveUserSession() {
        UserPreferences.saveUserId(activity!!, binding.user!!.id)
    }

    private fun confirmForwarderAction() {
        val action =
            ConfirmForwarderAuthFragmentDirections.actionAuthConfirmForwarderToChooseProcedure(binding.user!!)
        navController.navigate(action)
        activity!!.finish()
    }

    override fun handleScanPressButton() {
        confirmForwarderAction()
    }
}