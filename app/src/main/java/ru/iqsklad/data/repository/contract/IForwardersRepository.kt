package ru.iqsklad.data.repository.contract

import androidx.lifecycle.LiveData
import ru.iqsklad.data.dto.user.User

interface IForwardersRepository {

    fun getForwarders(searchText: String): LiveData<List<User>>
}