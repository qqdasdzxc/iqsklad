package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import ru.iqsklad.R
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.LoadingUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.dto.user.UserUI
import ru.iqsklad.databinding.FragmentChooseStewardBinding
import ru.iqsklad.presentation.implementation.user.ChooseUserViewModel
import ru.iqsklad.presentation.presenter.user.ChooseUserPresenter
import ru.iqsklad.ui.adapter.UsersAdapter
import ru.iqsklad.ui.base.fragment.BaseFragment

class ChooseStewardFragment : BaseFragment<FragmentChooseStewardBinding>(), UsersAdapter.UserClickListener {

    private lateinit var presenter: ChooseUserPresenter

    private var adapter = UsersAdapter(this)

    override fun getLayoutResId(): Int = R.layout.fragment_choose_steward

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = getPresenter<ChooseUserViewModel>()
        binding.presenter = presenter

        initObserve()
    }

    private fun initObserve() {
        presenter.getStewards().observe(this, Observer { uiModel ->
            when (uiModel) {
                LoadingUiModel -> {
                }
                is SuccessUiModel -> setUsers(uiModel.data)
                is ErrorUiModel -> showMessage(uiModel.error)
            }
        })
    }

    private fun setUsers(userList: List<UserUI>) {
        adapter.clear()
        adapter.addAll(userList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.chooseStewardRecyclerView.adapter = adapter

        binding.chooseStewardContinueWithoutStewardActionView.setOnClickListener {
            continueWithoutSteward()
        }

        binding.chooseStewardActionBarView.observeSearchText().observe(this, Observer {
            presenter.onSearchTextChanged(it)
        })
    }

    private fun continueWithoutSteward() {
        navController.navigate(ChooseStewardFragmentDirections.actionChooseStewardToInvoiceScan())
    }

    override fun onUserClicked(user: User) {
        val action = ChooseStewardFragmentDirections.actionChooseStewardToConfirmSteward(user)
        navController.navigate(action)
    }

    override fun handleScanPressButton() {
        continueWithoutSteward()
    }
}