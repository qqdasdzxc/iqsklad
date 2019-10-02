package ru.iqsklad.data.dto.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserRole(
    val id: Int,
    val description: String
): Parcelable