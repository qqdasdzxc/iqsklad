package ru.iqsklad.data.dto.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String,
    val ID: String
) : Parcelable