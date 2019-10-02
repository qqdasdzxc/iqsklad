package ru.iqsklad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.iqsklad.R
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.dto.user.UserUI
import ru.iqsklad.databinding.ItemChooseUserBinding
import ru.iqsklad.ui.base.adapter.RecyclerListAdapter
import ru.iqsklad.utils.extensions.hide
import ru.iqsklad.utils.extensions.show

class UsersAdapter(private var listener: UserClickListener) :
    RecyclerListAdapter<UserUI, UsersAdapter.UserViewHolder>() {

    override fun onBindCustomViewHolder(holder: UserViewHolder, position: Int) {
        holder.populate(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = DataBindingUtil.inflate<ItemChooseUserBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_choose_user,
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    inner class UserViewHolder(private var binding: ItemChooseUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun populate(userUI: UserUI) {
            binding.userUi = userUI
            binding.root.setOnClickListener { listener.onUserClicked(userUI.model) }

            setVisibilityFirstLetter(userUI.showFirstLetter)
        }

        private fun setVisibilityFirstLetter(visible: Boolean) {
            if (visible) {
                binding.itemChooseUserFirstLetter.show()
            } else {
                binding.itemChooseUserFirstLetter.hide()
            }
        }
    }

    interface UserClickListener {
        fun onUserClicked(user: User)
    }
}