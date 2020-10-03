package com.example.crossword_designer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data_table")
data class UserProfile(@PrimaryKey(autoGenerate = true) var userId: Long = 0L,
                       @ColumnInfo(name = "username") var userName: String = "Anon",
                       @ColumnInfo(name = "password") var password: String)