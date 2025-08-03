package com.example.quiz.presentation.ui.navigation.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.quiz.presentation.ui.homeScreen.HomePage
import com.example.quiz.presentation.ui.homeScreen.HomeSettings
import com.example.quiz.presentation.ui.homeScreen.ChangePasswordPage
import com.example.quiz.presentation.ui.homeScreen.ChangeUsernamePage
import com.example.quiz.presentation.ui.navigation.routeToSelectLevel
import com.example.quiz.presentation.ui.navigation.routes.HomeScreen
import com.example.quiz.presentation.ui.navigation.routes.RootScreen
import com.example.quiz.presentation.ui.theme.Dimens

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    onLogOut:()->Unit
){
    navigation(
        startDestination = HomeScreen.HomeMain.route,
        route = RootScreen.Home.route
    ){
        composable(
            route = HomeScreen.HomeMain.route,
        ){
            val context = LocalContext.current
            HomePage(
                onQuizSelectLevel = {categoryId, categoryTitle ->
                    val route = routeToSelectLevel(context,categoryId,categoryTitle)
                    navController.navigate(route)
                },
            )
        }
        composable(
            route = HomeScreen.HomeSettings.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = Dimens.AnimationDurationShort))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = Dimens.AnimationDurationShort)
                )
            }
        ){
            HomeSettings(
                navigateTo = {
                    navController.navigate(it)
                },
                onLogOut = {
                    onLogOut()
                }
            )
        }
        composable(
            route = HomeScreen.ChangeUsername.route
        ){
            ChangeUsernamePage(
                onSettings = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = HomeScreen.ChangePassword.route
        ){
            ChangePasswordPage(
                onSettings = {
                    navController.navigateUp()
                }
            )
        }
    }
}