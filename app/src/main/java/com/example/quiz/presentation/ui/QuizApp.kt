package com.example.quiz.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.quiz.data.models.BottomNavItem
import com.example.quiz.presentation.models.authScreenModels.AuthState
import com.example.quiz.presentation.ui.general.BottomNavigationBar
import com.example.quiz.presentation.ui.general.QuizakiTopAppBar
import com.example.quiz.presentation.ui.navigation.graphs.homeNavGraph
import com.example.quiz.presentation.ui.navigation.graphs.loginNavGraph
import com.example.quiz.presentation.ui.navigation.graphs.quizNavGraph
import com.example.quiz.presentation.ui.navigation.routes.HomeScreen
import com.example.quiz.presentation.ui.navigation.routes.QuizScreen
import com.example.quiz.presentation.ui.navigation.routes.RootScreen
import com.example.quiz.presentation.ui.theme.Dimens
import com.example.quiz.presentation.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel


@SuppressLint("SuspiciousIndentation")
@Composable
fun QuizApp() {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = koinViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val authState by viewModel.authState.collectAsState()


    LaunchedEffect(authState) {
        when(authState){
            is AuthState.Authenticated ->{
                navController.navigate(RootScreen.Home.route) {
                    popUpTo(0)
                }
            }
            is AuthState.Unauthenticated->{
                navController.navigate(RootScreen.Login.route) {
                    popUpTo(0)
                }
            }
            is AuthState.Loading ->{
                Unit
            }
        }
    }

    Scaffold(
        topBar = {
            when(currentRoute){
                QuizScreen.QuizSelectLevel.route,
                QuizScreen.QuizMain.route,
                HomeScreen.ChangePassword.route,
                HomeScreen.ChangeUsername.route->{
                    AnimatedBar(
                        initialOffsetY = {-it},
                        targetOffsetY = {-it},
                        content = {
                            QuizakiTopAppBar {
                                navController.navigateUp()
                            }
                        }
                    )
                }
                QuizScreen.QuizTotal.route ->{
                    AnimatedBar(
                        initialOffsetY = {-it},
                        targetOffsetY = {-it},
                        content = {
                            QuizakiTopAppBar {
                                navController.navigate(HomeScreen.HomeMain.route){
                                    popUpTo(0)
                                }
                            }
                        }
                    )
                }
            }
        },
        bottomBar = {
            when(currentRoute){
                HomeScreen.HomeMain.route,
                HomeScreen.HomeSettings.route->{
                    AnimatedBar(
                        initialOffsetY = {it},
                        targetOffsetY = {it},
                        content = {
                            BottomNavigationBar(
                                navController = navController,
                                currentRoute = currentRoute,
                                items = BottomNavItem.items
                            )
                        }
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding->
        Box(modifier = Modifier.padding(innerPadding)){
            NavHost(
                navController = navController,
                startDestination = "start"
            ){
                composable("start"){}
                loginNavGraph(navController)
                homeNavGraph(
                    navController = navController,
                    onLogOut = {
                        viewModel.logOut()
                    })
                quizNavGraph(navController)
            }
        }
    }
}

@Composable
fun AnimatedBar(
    content: @Composable  ()->Unit,
    initialOffsetY: (fullHeight:Int)-> Int = {it/2},
    targetOffsetY: (fullHeight:Int)-> Int = {it/2}
){
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            animationSpec = tween(Dimens.AnimationDurationShort),
            initialOffsetY = initialOffsetY
        ),
        exit = slideOutVertically(
            animationSpec = tween(Dimens.AnimationDurationShort),
            targetOffsetY = targetOffsetY
        ),
    ) {
        content()
    }
}