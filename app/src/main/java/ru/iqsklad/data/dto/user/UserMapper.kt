package ru.iqsklad.data.dto.user

object UserMapper {

    fun mapUsers(users: List<User>): List<UserUI> {
        val result = mutableListOf<UserUI>()
        users.forEachIndexed { index, user ->
            if (index == 0) {
                result.add(UserUI(user, true))
            } else {
                result.add(UserUI(user, users[index - 1].name[0] != user.name[0]))
            }
        }

        return result
    }
}