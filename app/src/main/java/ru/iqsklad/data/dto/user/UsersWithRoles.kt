package ru.iqsklad.data.dto.user

import com.google.gson.annotations.SerializedName

class UsersWithRoles(
    @SerializedName("position")
    val userRoles: List<UserRole>,
    @SerializedName("items")
    val users: List<User>
)