package com.example.quiz.presentation.ui.navigation.routes

sealed class LoginScreen(val route:String) {
    object LoginMain: LoginScreen("login")
    object LoginRegister: LoginScreen("register")
    object LoginRecover: LoginScreen("recover")
}