package com.example.quiz.presentation.ui.navigation.routes


sealed class  HomeScreen(val route:String){
    object HomeMain: HomeScreen("home")
    object HomeSettings: HomeScreen("settings")
    object ChangeUsername:HomeScreen("change_username")
    object ChangePassword:HomeScreen("change_password")
//    object LogOut:HomeScreen("log_out")
}