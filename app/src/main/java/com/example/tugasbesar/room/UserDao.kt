package com.example.tugasbesar.room

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(note: User)

    @Delete
    suspend fun deleteUser(note: User)

    @Query("SELECT * FROM User")
    suspend fun getUser() : List<User>
    @Query("SELECT * FROM User WHERE id =:user_id")
    suspend fun getUser(user_id: Int) : List<User>
}
