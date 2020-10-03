package com.example.crossword_designer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grid_table")
data class CrosswordGrid(@PrimaryKey(autoGenerate = true) var sessionId: Long = 0L,
                         @ColumnInfo(name = "username") val username: String = "Anon",
                         @ColumnInfo var board: String,
                         @ColumnInfo var clues: String)
                         

