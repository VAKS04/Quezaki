package com.example.quiz.domain.usecases

import com.example.quiz.domain.exceptions.ValidationException

class ValidateEmailUseCase {
    private companion object {
        val EMAIL_REGEX = Regex(
            pattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        )
    }

    operator fun invoke(email: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(ValidationException("Email cannot be blank"))
        }

        return if (EMAIL_REGEX.matches(email)) {
            Result.success(Unit)
        } else {
            Result.failure(ValidationException("Invalid email format"))
        }
    }
}