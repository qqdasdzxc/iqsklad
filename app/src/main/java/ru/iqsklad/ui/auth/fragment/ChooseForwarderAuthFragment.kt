package ru.iqsklad.ui.auth.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.iqsklad.R
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentAuthChooseForwarderBinding
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.implementation.auth.ChooseForwarderViewModel
import ru.iqsklad.presentation.presenter.auth.ChooseForwarderPresenter
import ru.iqsklad.ui.adapter.UsersAdapter
import ru.iqsklad.utils.extensions.injectViewModel
import javax.inject.Inject

class ChooseForwarderAuthFragment : BaseFragment<FragmentAuthChooseForwarderBinding>(), UsersAdapter.UserClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var presenter: ChooseForwarderPresenter

    private var adapter = UsersAdapter(this)

    override fun getLayoutResId(): Int = R.layout.fragment_auth_choose_forwarder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.authComponent?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = injectViewModel<ChooseForwarderViewModel>(viewModelFactory)
        initObserve()
    }

    private fun initObserve() {
        presenter.getForwarders().observe(this, Observer { setUsers(it) })
    }

    private fun setUsers(userList: List<User>) {
        adapter.clear()
        adapter.addAll(userList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.chooseForwarderRecyclerView.adapter = adapter
    }

    override fun onUserClicked(user: User) {
        val action = ChooseForwarderAuthFragmentDirections.actionAuthChooseForwarderToConfirmForwarder(user)
        navController.navigate(action)
    }

    override fun handleScanPressButton() {
        showMessage(R.string.auth_choose_error_title)
    }
}