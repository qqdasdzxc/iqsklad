package ru.iqsklad.ui.auth.fragment

import android.os.Bundle
import android.view.View
import ru.iqsklad.R
import ru.iqsklad.databinding.FragmentAuthConfirmForwarderBinding
import ru.iqsklad.ui.base.fragment.BaseFragment

class ConfirmForwarderAuthFragment : BaseFragment<FragmentAuthConfirmForwarderBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_auth_confirm_forwarder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.user = ConfirmForwarderAuthFragmentArgs.fromBundle(arguments!!).user

        binding.confirmUserChangeView.setOnClickListener { navController.navigateUp() }

        binding.confirmUserAcceptView.setOnClickListener {
            val action =
                ConfirmForwarderAuthFragmentDirections.actionAuthConfirmForwarderToChooseProcedure(binding.user!!)
            navController.navigate(action)
            activity!!.finish()
        }
    }
}