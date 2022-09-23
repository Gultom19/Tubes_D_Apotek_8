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

    @Query("SELECT * FROM User WHERE id =:note_id")
    suspend fun getUserId(note_id: Int) : List<User>

    @Query("SELECT * FROM User WHERE username =:username")
    suspend fun getUsername(username: String) : List<User>

    @Query("SELECT * FROM User WHERE password =:password")
    suspend fun getPassword(password: String) : List<User>
}
