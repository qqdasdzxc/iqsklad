package ru.iqsklad.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.iqsklad.R
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.databinding.ItemChooseUserBinding
import ru.iqsklad.ui.base.adapter.RecyclerListAdapter

class UsersAdapter(private var listener: UserClickListener) : RecyclerListAdapter<User, UsersAdapter.UserViewHolder>() {

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

        fun populate(user: User) {
            binding.itemChooseUserName.text = user.name
            binding.root.setOnClickListener { listener.onUserClicked(user) }
        }
    }

    interface UserClickListener {
        fun onUserClicked(user: User)
    }
}