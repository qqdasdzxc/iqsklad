package ru.iqsklad.ui.procedure.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.iqsklad.R
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.dto.user.UserUI
import ru.iqsklad.databinding.FragmentChooseStewardBinding
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.implementation.procedure.ChooseStewardViewModel
import ru.iqsklad.presentation.presenter.procedure.ChooseStewardPresenter
import ru.iqsklad.ui.adapter.UsersAdapter
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.utils.extensions.injectViewModel
import javax.inject.Inject

class ChooseStewardFragment : BaseFragment<FragmentChooseStewardBinding>(), UsersAdapter.UserClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var presenter: ChooseStewardPresenter

    private var adapter = UsersAdapter(this)

    override fun getLayoutResId(): Int = R.layout.fragment_choose_steward

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.procedureComponent?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = injectViewModel<ChooseStewardViewModel>(viewModelFactory)
        binding.presenter = presenter

        initObserve()
    }

    private fun initObserve() {
        presenter.getStewards().observe(this, Observer { setUsers(it) })
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