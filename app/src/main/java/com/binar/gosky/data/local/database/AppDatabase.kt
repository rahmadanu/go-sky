package com.binar.gosky.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binar.gosky.data.local.database.tickets.TicketsDao
import com.binar.gosky.data.local.model.TicketsItemWishlist
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [TicketsItemWishlist::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun ticketsDao(): TicketsDao

    companion object {
        private const val DB_NAME = "wishlistTickets.db"

        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    val passphrase: ByteArray =
                        SQLiteDatabase.getBytes("wishlistTickets-hashed".toCharArray())
                    val factory = SupportFactory(passphrase)

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .openHelperFactory(factory)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}