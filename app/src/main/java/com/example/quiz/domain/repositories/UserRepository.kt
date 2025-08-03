package com.example.quiz.domain.repositories

import com.example.quiz.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User):Result<Unit>
    suspend fun updateUser(user: User):Result<Unit>
    suspend fun countUsers(email:String):Int
    fun getUser(email: String,password: String):Flow<User?>
}