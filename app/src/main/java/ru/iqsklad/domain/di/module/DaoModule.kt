package ru.iqsklad.domain.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.iqsklad.data.db.Database
import ru.iqsklad.data.db.dao.MainDao
import javax.inject.Singleton

@Module
class DaoModule {

    @Singleton
    @Provides
    fun getDataBase(context: Context) = Room.databaseBuilder(context, Database::class.java, Database.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun getMainDao(database: Database): MainDao = database.getMainDao()
}