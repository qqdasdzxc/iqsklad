package ru.iqsklad.data.repository.contract

import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.user.User

interface IUsersRepository {

    fun getUsers(): LiveData<List<User>>
}