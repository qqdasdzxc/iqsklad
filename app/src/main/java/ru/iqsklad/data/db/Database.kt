package ru.iqsklad.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.iqsklad.data.db.dao.MainDao
import ru.iqsklad.data.dto.user.User

@Database(entities = [User::class], exportSchema = false, version = 1)
abstract class Database : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "s7-db"
    }

    abstract fun getMainDao(): MainDao
}