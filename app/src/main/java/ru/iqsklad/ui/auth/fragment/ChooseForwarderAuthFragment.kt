package ru.iqsklad.ui.auth.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import ru.iqsklad.R
import ru.iqsklad.data.dto.ui.ErrorUiModel
import ru.iqsklad.data.dto.ui.LoadingUiModel
import ru.iqsklad.data.dto.ui.SuccessUiModel
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.dto.user.UserUI
import ru.iqsklad.databinding.FragmentAuthChooseForwarderBinding
import ru.iqsklad.presentation.implementation.user.ChooseUserViewModel
import ru.iqsklad.presentation.presenter.user.ChooseUserPresenter
import ru.iqsklad.ui.adapter.UsersAdapter
import ru.iqsklad.ui.base.fragment.BaseFragment

class ChooseForwarderAuthFragment : BaseFragment<FragmentAuthChooseForwarderBinding>(), UsersAdapter.UserClickListener {

    private lateinit var presenter: ChooseUserPresenter

    private var adapter = UsersAdapter(this)

    override fun getLayoutResId(): Int = R.layout.fragment_auth_choose_forwarder

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = getPresenter<ChooseUserViewModel>()
        initObserve()
    }

    private fun initObserve() {
        presenter.getForwarders(activity!!).observe(this, Observer { uiModel ->
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
        binding.chooseForwarderRecyclerView.adapter = adapter

        binding.chooseForwarderActionBarView.observeSearchText().observe(this, Observer {
            presenter.onSearchTextChanged(it)
        })
    }

    override fun onUserClicked(user: User) {
        val action = ChooseForwarderAuthFragmentDirections.actionAuthChooseForwarderToConfirmForwarder(user)
        navController.navigate(action)
    }

    override fun handleScanPressButton() {
        showMessage(R.string.auth_choose_error_title)
    }
}