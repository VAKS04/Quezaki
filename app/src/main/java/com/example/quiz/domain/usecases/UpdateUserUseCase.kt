package com.example.quiz.domain.usecases

import com.example.quiz.domain.models.User
import com.example.quiz.domain.repositories.UserRepository

class UpdateUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User):Result<Unit>{
        return runCatching {
            repository.updateUser(user)
        }
    }
}