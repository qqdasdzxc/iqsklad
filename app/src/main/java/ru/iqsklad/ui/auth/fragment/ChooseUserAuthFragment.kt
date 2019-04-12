package ru.iqsklad.ui.auth.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.iqsklad.R
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.ui.base.fragment.BaseFragment
import ru.iqsklad.databinding.FragmentAuthChooseUserBinding
import ru.iqsklad.domain.App
import ru.iqsklad.presentation.implementation.auth.ChooseUserViewModel
import ru.iqsklad.presentation.presenter.auth.ChooseUserPresenter
import ru.iqsklad.ui.auth.adapter.UsersAdapter
import ru.iqsklad.utils.extensions.injectViewModel
import javax.inject.Inject

class ChooseUserAuthFragment : BaseFragment<FragmentAuthChooseUserBinding>(), UsersAdapter.UserClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var presenter: ChooseUserPresenter

    private var adapter = UsersAdapter(this)

    override fun getLayoutResId(): Int = R.layout.fragment_auth_choose_user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.authComponent?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = injectViewModel<ChooseUserViewModel>(viewModelFactory)
        initObserve()
    }

    private fun initObserve() {
        presenter.getUsers().observe(this, Observer { setUsers(it) })
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
        binding.chooseUserRecyclerView.adapter = adapter
    }

    override fun onUserClicked(user: User) {
        val action = ChooseUserAuthFragmentDirections.actionAuthChooseUserToConfirmUser(user)
        navController.navigate(action)
    }
}