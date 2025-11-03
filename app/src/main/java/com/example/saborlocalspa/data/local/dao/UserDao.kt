package com.example.saborlocalspa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.saborlocalspa.data.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}
