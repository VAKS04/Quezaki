package com.example.quiz.domain.usecases

import com.example.quiz.domain.repositories.UserRepository

class IsExistUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke (email:String):Boolean{
        return repository.countUsers(email) > 0
    }
}