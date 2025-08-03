package com.example.quiz.presentation.ui.navigation.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.quiz.presentation.ui.authScreen.LoginPage
import com.example.quiz.presentation.ui.authScreen.RegisterPage
import com.example.quiz.presentation.ui.authScreen.general.AuthScreen
import com.example.quiz.presentation.ui.navigation.routes.HomeScreen
import com.example.quiz.presentation.ui.navigation.routes.LoginScreen
import com.example.quiz.presentation.ui.navigation.routes.RootScreen
import com.example.quiz.presentation.ui.theme.Dimens


fun NavGraphBuilder.loginNavGraph(navController: NavController){
    navigation(
        startDestination = LoginScreen.LoginMain.route,
        route = RootScreen.Login.route
    ){
        composable(
            route = LoginScreen.LoginMain.route,
        ){
            AuthScreen {
                LoginPage(
                    onRegisterClick = {
                        navController.navigate(LoginScreen.LoginRegister.route){
                            popUpTo(0)
                        }
                    },
                    onHomeClick = {
                        navController.navigate(HomeScreen.HomeMain.route){
                            popUpTo(0)
                        }
                    },
                )
            }
        }
        composable(
            route = LoginScreen.LoginRegister.route,
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(durationMillis = Dimens.AnimationDurationShort),
                    initialOffsetY = {it}
                )
            },
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(durationMillis = Dimens.AnimationDurationShort),
                    targetOffsetY = {it}
                )
            }
        ){
            AuthScreen {
                RegisterPage(
                    onLoginClick = {
                        navController.navigate(LoginScreen.LoginMain.route){
                            popUpTo(0)
                        }
                    },
                )
            }
        }
        composable(LoginScreen.LoginRecover.route){

        }
    }
}