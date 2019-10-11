package ru.iqsklad.data.dto.user

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
open class User(
    val id: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("position")
    val position: String
) : Parcelable {

    fun getFullName(): String = "$lastName $firstName $middleName"

    fun getFirstLetterString(): String = lastName[0].toString()
}