package com.example.fundamentalpertama.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Table::class], version = 2, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = FavoriteDatabase::class.java,
                    name = "favorite_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }

        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Menambahkan kolom baru
                database.execSQL("ALTER TABLE table_favorite ADD COLUMN img TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE table_favorite ADD COLUMN category TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE table_favorite ADD COLUMN name TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE table_favorite ADD COLUMN ownerName TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE table_favorite ADD COLUMN summary TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE table_favorite ADD COLUMN beginTime TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE table_favorite ADD COLUMN endTime TEXT DEFAULT ''")
            }
        }
    }
}