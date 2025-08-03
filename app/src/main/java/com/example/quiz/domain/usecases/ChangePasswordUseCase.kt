package com.example.quiz.domain.usecases

import com.example.quiz.presentation.models.UserUiState

class ChangePasswordUseCase {
    operator fun invoke(
        password:String,
        userUiState: UserUiState
    ):UserUiState{
        return userUiState.copy(
            password = password
        )
    }
}