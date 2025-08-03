package com.example.quiz.domain.usecases

import com.example.quiz.domain.models.User
import com.example.quiz.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class CheckUserCacheUseCase(
    private val repository:UserPreferencesRepository
) {
    operator fun invoke (): Flow<User?>{
        return repository.readUserData()
    }
}