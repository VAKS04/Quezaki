package com.example.quiz.presentation.ui.navigation.routes

sealed class RootScreen (val route:String){
    object Home:RootScreen("home_graph?")
    object Login:RootScreen("login_graph")
    object Quiz:RootScreen("quiz_graph")
}