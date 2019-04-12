package ru.iqsklad.ui.auth.fragment

import android.os.Bundle
import android.view.View
import ru.iqsklad.R
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentAuthConfirmUserBinding

class ConfirmUserAuthFragment: BaseFragment<FragmentAuthConfirmUserBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_auth_confirm_user

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.user = ConfirmUserAuthFragmentArgs.fromBundle(arguments!!).user

        binding.confirmUserChangeView.setOnClickListener { navController.navigateUp() }
    }
}