package com.example.quiz.presentation.models

data class UserUiState (
    val id: Int,
    val email:String,
    val username : String,
    val password:String,
    val avatarPath:String?
)