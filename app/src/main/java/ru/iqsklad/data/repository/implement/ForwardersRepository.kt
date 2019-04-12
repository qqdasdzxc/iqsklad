package ru.iqsklad.data.repository.implement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.iqsklad.data.dto.user.Forwarder
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.repository.contract.IForwardersRepository

class ForwardersRepository : IForwardersRepository {

    private val stubUsersList = listOf(
        Forwarder("Кузьмин Дмитрий Игоревич", "111111"),
        Forwarder("Иванов Иван Иванович", "222222"),
        Forwarder("Петров Перт Петрович", "333333"),
        Forwarder("Александров Сан Саныч", "444444")
    )

    override fun getForwarders(): LiveData<List<User>> {
        val result = MutableLiveData<List<User>>()
        result.postValue(stubUsersList)
        return result
    }
}