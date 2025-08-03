package com.example.quiz.domain.usecases

import com.example.quiz.domain.exceptions.UserNotFoundException
import com.example.quiz.domain.models.User
import com.example.quiz.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AuthorizationUseCase(
    private val repository: UserRepository
){
    suspend operator fun invoke(
        email:String,
        password:String
    ):User{
        val result = repository.getUser(email = email, password = password).firstOrNull()
            ?: throw UserNotFoundException()

        return result
    }
}