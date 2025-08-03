package com.example.quiz.presentation.ui.general

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.quiz.data.models.BottomNavItem
import com.example.quiz.presentation.ui.theme.Shapes


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String,
    items: List<BottomNavItem>
) {
    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(
            topStart = Shapes.navigationBarShape,
            topEnd = Shapes.navigationBarShape
        )),
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route){
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.inverseSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.inverseSurface,
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}
