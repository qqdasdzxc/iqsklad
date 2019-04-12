package ru.iqsklad.ui.auth.fragment

import android.os.Bundle
import android.view.View
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentAuthConfirmForwarderBinding

class ConfirmForwarderAuthFragment: BaseFragment<FragmentAuthConfirmForwarderBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_auth_confirm_forwarder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.forwarder = ConfirmForwarderAuthFragmentArgs.fromBundle(arguments!!).forwarder

        binding.confirmUserChangeView.setOnClickListener { navController.navigateUp() }
    }
}