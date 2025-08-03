package com.example.quiz.data.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.quiz.presentation.ui.navigation.routes.HomeScreen

sealed class BottomNavItem(
    val route:String,
    val label:String,
    val icon: ImageVector,
){
    object Home: BottomNavItem(
        route = HomeScreen.HomeMain.route,
        label = "Home",
        icon = Icons.Default.Home
    )

    object Settings: BottomNavItem(
        route = HomeScreen.HomeSettings.route,
        label = "Settings",
        icon = Icons.Default.Settings
    )
    companion object {
        val items = listOf(Home,Settings)
    }
}