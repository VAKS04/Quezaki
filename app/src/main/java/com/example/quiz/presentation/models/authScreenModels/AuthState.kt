package com.example.quiz.presentation.models.authScreenModels

sealed interface AuthState {
    object Loading : AuthState
    object Authenticated : AuthState
    object Unauthenticated : AuthState
}