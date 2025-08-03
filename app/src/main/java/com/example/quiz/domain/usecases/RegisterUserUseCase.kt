package com.example.quiz.domain.usecases

import com.example.quiz.domain.exceptions.UserExistException
import com.example.quiz.domain.models.User
import com.example.quiz.domain.repositories.UserRepository

class RegisterUserUseCase(
    private val repository: UserRepository,
    private val isExistUserUseCase: IsExistUserUseCase
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String
    ) {
        val userExists = isExistUserUseCase(email)
        if (userExists) {
            throw UserExistException()
        }

        repository.insertUser(
            User(
                username = username,
                email = email,
                password = password
            )
        )
    }
}