package com.example.quiz.domain.usecases

import com.example.quiz.domain.repositories.UserPreferencesRepository

class ClearUserCacheUseCase(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke (){
        repository.clearUserData()
    }
}