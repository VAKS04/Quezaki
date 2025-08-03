package com.example.quiz.domain.usecases

import com.example.quiz.domain.exceptions.ValidationException

class ValidatePasswordUseCase {
    private companion object {
        const val MIN_LENGTH = 8
    }

    operator fun invoke(password: String): Result<Unit> {
        return when {
            password.isEmpty() ->
                Result.failure(ValidationException("Password cannot be empty"))

            password.length < MIN_LENGTH ->
                Result.failure(ValidationException("Password must be at least $MIN_LENGTH characters long"))

            else ->
                Result.success(Unit)
        }
    }
}