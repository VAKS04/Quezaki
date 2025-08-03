package com.example.quiz.domain.usecases

import com.example.quiz.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ReadPasswordFromCacheUseCase(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<String?> {
        return repository.readPassword()
    }
}