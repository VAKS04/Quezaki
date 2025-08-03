package com.example.quiz.domain.usecases

import com.example.quiz.domain.exceptions.ValidationException

class ValidateUsernameUseCase {
    private companion object {
        const val MIN_LENGTH = 4
        val ALLOWED_SYMBOLS = Regex("^[a-zA-Z0-9_.-]+\$")
    }

    operator fun invoke(username: String): Result<Unit> = runCatching {
        require(username.isNotEmpty()) { "Username can't be empty" }
        require(username.length >= MIN_LENGTH) {
            "Username must be at least $MIN_LENGTH characters long"
        }
        require(ALLOWED_SYMBOLS.matches(username)) {
            "Username can only contain letters, numbers, and .-_ symbols"
        }
    }.fold(
        onSuccess = { Result.success(Unit) },
        onFailure = { e ->
            Result.failure(ValidationException(e.message ?: "Invalid username"))
        }
    )
}