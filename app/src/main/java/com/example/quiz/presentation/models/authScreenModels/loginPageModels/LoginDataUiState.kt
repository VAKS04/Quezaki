package com.example.quiz.presentation.models.authScreenModels.loginPageModels

data class LoginDataUiState(
    val email:String = "",
    val password:String = "",
    val isButtonEnabled:Boolean = false
)