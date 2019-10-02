package ru.iqsklad.data.repository.implement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.repository.contract.IStewardsRepository

class StewardsRepository : IStewardsRepository {

    private val stubUsersList = listOf(
        User(id = "111111", lastName = "Кузьмин", firstName = "Дмитрий", middleName = "Игоревич", position = "2"),
        User(id = "222222", lastName = "Иванов", firstName = "Иван", middleName = "Иванович", position = "2"),
        User(id = "333333", lastName = "Петров", firstName = "Перт", middleName = "Петрович", position = "2"),
        User(id = "444444", lastName = "Александров", firstName = "Сан", middleName = "Саныч", position = "1")
    )

    override fun getStewards(searchText: String): LiveData<List<User>> {
        val result = MutableLiveData<List<User>>()
        result.postValue(stubUsersList.filter {
            it.lastName.toLowerCase().contains(searchText.trim())
        }.sortedBy { it.lastName })
        return result
    }
}