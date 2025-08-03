package com.example.quiz.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Query("SELECT COUNT(*) FROM user WHERE email = :email")
    suspend fun countUsersByEmail(email:String):Int

    @Query("SELECT *  FROM user where email =:email and password =:password")
    fun getItem(email:String, password:String): Flow<UserEntity?>
}