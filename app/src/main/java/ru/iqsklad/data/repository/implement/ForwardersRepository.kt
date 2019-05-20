package ru.iqsklad.data.repository.implement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.repository.contract.IForwardersRepository

class ForwardersRepository : IForwardersRepository {

    private val stubUsersList = listOf(
        User("Кузьмин Дмитрий Игоревич", "111111"),
        User("Кузьмин Дмитрий Игоревич", "111112"),
        User("Иванов Иван Иванович", "222222"),
        User("Петров Перт Петрович", "333333"),
        User("Александров Сан Саныч", "444444")
    )

    override fun getForwarders(searchText: String): LiveData<List<User>> {
        val result = MutableLiveData<List<User>>()
        result.postValue(stubUsersList.filter {
            it.name.toLowerCase().contains(searchText.trim())
        }.sortedBy { it.name })
        return result
    }
}