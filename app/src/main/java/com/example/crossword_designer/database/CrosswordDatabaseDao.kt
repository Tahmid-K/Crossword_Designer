package com.example.crossword_designer.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CrosswordDatabaseDao {
    @Insert
    fun insert(aCrossword: CrosswordGrid)
    @Insert
    fun insert(aUser: UserProfile)

    @Delete
    fun delete(aCrossword: CrosswordGrid)
    @Delete
    fun delete(aUser: UserProfile)

    @Query("DELETE FROM grid_table")
    fun clearSessionDatabase()

    @Query("DELETE FROM user_data_table")
    fun clearUserDatabase()

    @Update
    fun update(aCrossword: CrosswordGrid)
    @Update
    fun update(aUser: UserProfile)

    @Query("SELECT * FROM grid_table WHERE username = :aUsername ORDER BY sessionID DESC")
    fun getUserSessions(aUsername: String) : LiveData<List<CrosswordGrid>>

    @Query("SELECT COUNT(userId) FROM user_data_table WHERE username = :aUsername AND password = :aPassword")
    fun validateUser(aUsername: String, aPassword: String) : Boolean

    @Query("SELECT * FROM grid_table")
    fun getAllSessions() : List<CrosswordGrid>

    @Query("SELECT * FROM user_data_table")
    fun getAllUsers() : List<UserProfile>
}