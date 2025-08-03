package com.example.quiz.domain.usecases

import com.example.quiz.domain.models.User
import com.example.quiz.domain.repositories.UserPreferencesRepository

class SaveUserCacheUseCase(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke (user: User){
        repository.editUserData(user)
    }
}