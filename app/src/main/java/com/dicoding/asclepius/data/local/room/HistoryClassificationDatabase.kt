package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.HistoryClassification

@Database(entities = [HistoryClassification::class], version = 1, exportSchema = false)
abstract class HistoryClassificationDatabase : RoomDatabase(){

    abstract fun historyClassificationDao() : HistoryClassificationDao

    companion object {
        private fun buildDatabase(context: Context) : HistoryClassificationDatabase {
            return Room.databaseBuilder(
                context,
                HistoryClassificationDatabase::class.java,
                "cancer_detection"
            ).build()
        }

        @Volatile
        private var INSTANCE : HistoryClassificationDatabase? = null

        fun getDatabaseInstance(context: Context) : HistoryClassificationDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }
    }

}