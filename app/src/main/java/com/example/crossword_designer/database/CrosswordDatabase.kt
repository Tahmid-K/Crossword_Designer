package com.example.crossword_designer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CrosswordGrid::class, UserProfile::class], version = 1,  exportSchema = false)
abstract class CrosswordDatabase : RoomDatabase() {
    abstract val theDao : CrosswordDatabaseDao
    companion object {
        @Volatile
        private var theInstance: CrosswordDatabase? = null
        fun getInstance(aContext: Context) : CrosswordDatabase {
            synchronized(this) {
                var anInstance = theInstance
                if(anInstance == null) {
                    anInstance = Room.databaseBuilder(
                        aContext.applicationContext,
                        CrosswordDatabase::class.java,
                        "session_database"
                    ).fallbackToDestructiveMigration().build()
                    theInstance = anInstance
                }
                return anInstance
            }
        }
    }
}