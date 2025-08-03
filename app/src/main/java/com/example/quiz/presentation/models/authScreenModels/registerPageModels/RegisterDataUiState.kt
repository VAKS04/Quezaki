package com.example.quiz.presentation.models.authScreenModels.registerPageModels


data class RegisterDataUiState(
    val username: String = "",
    val email:String = "",
    val password:String = "",
    val isButtonEnabled:Boolean = false
)