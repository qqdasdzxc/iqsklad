package ru.iqsklad.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.iqsklad.data.db.convert.InvoiceEquipmentsConverter
import ru.iqsklad.data.db.convert.ScannedRfidsConverter
import ru.iqsklad.data.db.dao.MainDao
import ru.iqsklad.data.dto.procedure.Invoice
import ru.iqsklad.data.dto.procedure.ProcedureResult
import ru.iqsklad.data.dto.rfid.Rfid
import ru.iqsklad.data.dto.user.User

@Database(entities = [User::class, Rfid::class, Invoice::class, ProcedureResult::class], exportSchema = false, version = 1)
@TypeConverters(
    value = [InvoiceEquipmentsConverter::class, ScannedRfidsConverter::class]
)
abstract class Database : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "s7-db"
    }

    abstract fun getMainDao(): MainDao
}