package com.example.quiz.domain.usecases

import com.example.quiz.domain.exceptions.ValidationException
import kotlinx.coroutines.flow.first

class ValidateOldPasswordUseCase(
    private val readPasswordFromCacheUseCase: ReadPasswordFromCacheUseCase
) {
    suspend operator fun invoke(inputPassword: String): Result<Unit> {
        return try {
            val storedPassword = readPasswordFromCacheUseCase().first()

            if (inputPassword == storedPassword) {
                Result.success(Unit)
            } else {
                Result.failure(ValidationException("Wrong old password"))
            }
        } catch (e: Exception) {
            Result.failure(ValidationException("Password validation failed"))
        }
    }
}