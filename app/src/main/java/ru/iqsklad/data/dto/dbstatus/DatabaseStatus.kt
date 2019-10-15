package ru.iqsklad.data.dto.dbstatus

import androidx.annotation.StringRes
import ru.iqsklad.R

sealed class DatabaseStatus(
    @StringRes val stringID: Int,
    val imageResourceID: Int
) {
    object NotUpdated: DatabaseStatus(R.string.status_not_updated, R.mipmap.status_not_updated)
    object Updated: DatabaseStatus(R.string.status_updated, R.mipmap.status_updated)
    object Updating: DatabaseStatus(R.string.status_updating, R.mipmap.status_updating)
}