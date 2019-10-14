package ru.iqsklad.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.iqsklad.data.dto.user.User

@Dao
interface MainDao {

    @Query("SELECT * FROM user WHERE position LIKE :typeID")
    fun getUsers(typeID: Int): List<User>

    @Query("SELECT * FROM user WHERE " +
            "position LIKE :typeID AND firstName LIKE '%' || :searchString || '%'" +
            "OR position LIKE :typeID AND middleName LIKE '%' || :searchString || '%'" +
            "OR position LIKE :typeID AND lastName LIKE '%' || :searchString || '%'")
    fun getUsers(typeID: Int, searchString: String): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUsers(users: List<User>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun saveInvoices(invoices: List<Invoice>)
}