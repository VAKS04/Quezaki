package com.example.quiz.presentation.models

import com.example.quiz.R
import com.example.quiz.presentation.ui.navigation.routes.HomeScreen

object Settings {
    val items = listOf(
        SettingsItem(
            icon = R.drawable.change_icon,
            title = R.string.change_username,
            route = HomeScreen.ChangeUsername.route
        ),
        SettingsItem(
            icon = R.drawable.change_icon,
            title = R.string.change_password,
            route = HomeScreen.ChangePassword.route
        ),
    )
}