package com.example.quiz.presentation.models.authScreenModels.registerPageModels

sealed class RegisterPageUiState{
    object Idle: RegisterPageUiState()
    object UserExist: RegisterPageUiState()
    data class Error(val message:String): RegisterPageUiState()
    object Success: RegisterPageUiState()
}