package com.example.quiz.presentation.models.authScreenModels.loginPageModels

sealed class LoginPageUiState{
    object Idle: LoginPageUiState()
    object UserNotFound: LoginPageUiState()
    data class Error(val message:String): LoginPageUiState()
    object Success: LoginPageUiState()
}