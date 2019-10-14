package ru.iqsklad.data.dto.user

sealed class UserType(var roleID: Int) {
    object Forwarder : UserType(1)
    object Steward : UserType(2)
}