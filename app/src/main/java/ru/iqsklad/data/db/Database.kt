package ru.iqsklad.data.db

import androidx.room.RoomDatabase

abstract class Database : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "s7-db"
    }
}