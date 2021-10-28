package com.example.crosstext.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CopyData::class],version = 1)
abstract class CopyDatabase : RoomDatabase() {
    abstract fun getCopyDao():CopyDao

    companion object{
        @Volatile
        private var INSTANCE:CopyDatabase? = null

        fun getDatabase(context: Context):CopyDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CopyDatabase::class.java,
                    "copy_Database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}