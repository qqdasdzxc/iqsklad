package ru.iqsklad.data.repository.implement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import ru.dtk.lib.network.builder.DtkNetBuilder
import ru.iqsklad.data.dto.user.User
import ru.iqsklad.data.repository.contract.IForwardersRepository
import ru.iqsklad.data.web.api.UsersApi
import ru.iqsklad.data.web.factory.RequestBuilder
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class ForwardersRepository @Inject constructor(
    private var api: UsersApi,
    private var controller: DtkNetBuilder,
    private var requestBuilder: RequestBuilder
): IForwardersRepository {

    private val stubUsersList = listOf(
        User(id = "111111", lastName = "Кузьмин", firstName = "Дмитрий", middleName = "Игоревич", position = "1"),
        User(id = "111112", lastName = "Кузьмин", firstName = "Дмитрий", middleName = "Игоревич", position = "1"),
        User(id = "222222", lastName = "Иванов", firstName = "Иван", middleName = "Иванович", position = "1"),
        User(id = "333333", lastName = "Петров", firstName = "Перт", middleName = "Петрович", position = "1"),
        User(id = "444444", lastName = "Александров", firstName = "Сан", middleName = "Саныч", position = "2")
    )

    override fun getForwarders(searchText: String): LiveData<List<User>> {
        runBlocking {
            val job = async {
                api.getUsersAsync(
                    requestBuilder
                        .createRequest()
                        .setMethod("person.getList")
                        .build()
                ).await()
            }
            val response = job.await()

            val job1 = async {
                api.getEquipmentsAsync(
                    requestBuilder
                        .createRequest()
                        .setMethod("rfid.getList")
                        .build()
                ).await()
            }
            val response1 = job1.await()
            val asd = String()
        }

        val result = MutableLiveData<List<User>>()
        result.postValue(stubUsersList.filter {
            it.lastName.toLowerCase().contains(searchText.trim())
        }.sortedBy { it.lastName })
        return result
    }
}