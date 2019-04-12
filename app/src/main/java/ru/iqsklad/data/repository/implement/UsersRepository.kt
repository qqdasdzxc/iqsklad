package ru.iqsklad.data.repository.implement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.repository.contract.IUsersRepository

class UsersRepository : IUsersRepository {

    private val stubUsersList = listOf(
        User("Кузьмин Дмитрий Игоревич", "111111"),
        User("Иванов Иван Иванович", "222222"),
        User("Петров Перт Петрович", "333333"),
        User("Александров Сан Саныч", "444444")
    )

    override fun getUsers(): LiveData<List<User>> {
        val result = MutableLiveData<List<User>>()
        result.postValue(stubUsersList)
        return result
    }
}