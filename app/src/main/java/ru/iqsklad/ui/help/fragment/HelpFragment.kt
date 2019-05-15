package ru.iqsklad.ui.help.fragment

import android.os.Bundle
import android.view.View
import ru.iqsklad.R
import ru.iqsklad.databinding.FragmentHelpBinding
import ru.iqsklad.ui.base.fragment.BaseFragment

class HelpFragment: BaseFragment<FragmentHelpBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_help

    override fun handleScanPressButton() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.helpActionBar.hideMoreButton()
    }
}