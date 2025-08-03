package com.example.quiz.domain.repositories

import com.example.quiz.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun editUserData(user: User)
    fun readUserData(): Flow<User?>
    fun readUsername():Flow<String?>
    fun readPassword():Flow<String?>
    suspend fun clearUserData()
}