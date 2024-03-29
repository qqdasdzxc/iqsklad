package ru.iqsklad.data.dto.user

object UsersMapper {

    fun mapUsersForShowFirstLetter(users: List<User>?): List<UserUI> {
        val result = mutableListOf<UserUI>()
        users?.forEachIndexed { index, user ->
            if (index == 0) {
                result.add(UserUI(user, true))
            } else {
                result.add(UserUI(user, users[index - 1].lastName[0] != user.lastName[0]))
            }
        }

        return result.sortedBy { it.model.lastName }
    }

    fun map(usersWithRoles: UsersWithRoles) {
        usersWithRoles.users?.forEach { user ->
            user.description = findRoleDescription(usersWithRoles.userRoles, user.position)
        }
    }

    private fun findRoleDescription(
        userRoles: List<UserRole>,
        userRoleID: String
    ): String? = userRoles.firstOrNull { role -> role.id.toString() == userRoleID }?.description
}