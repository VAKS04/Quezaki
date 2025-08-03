package com.example.quiz.presentation.ui.navigation.graphs

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.quiz.R
import com.example.quiz.data.models.Category
import com.example.quiz.presentation.ui.navigation.NavigationConst
import com.example.quiz.presentation.ui.navigation.routeToQuiz
import com.example.quiz.presentation.ui.navigation.routeToQuizTotal
import com.example.quiz.presentation.ui.navigation.routes.HomeScreen
import com.example.quiz.presentation.ui.navigation.routes.QuizScreen
import com.example.quiz.presentation.ui.navigation.routes.RootScreen
import com.example.quiz.presentation.ui.quizScreen.QuizPage
import com.example.quiz.presentation.ui.quizScreen.SelectLevelPage
import com.example.quiz.presentation.ui.quizScreen.TotalPage
import com.example.quiz.presentation.ui.theme.Dimens

@SuppressLint("SuspiciousIndentation")
fun NavGraphBuilder.quizNavGraph(navController: NavController){
    navigation(
        startDestination = QuizScreen.QuizMain.route,
        route = RootScreen.Quiz.route
    ){
        composable(
            route = QuizScreen.QuizMain.route,
            arguments = listOf(
                navArgument(NavigationConst.CATEGORY_ID_NAME){
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(NavigationConst.AMOUNT_NAME){
                    type = NavType.StringType
                },
                navArgument(NavigationConst.DIFFICULTY_NAME){
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(NavigationConst.TYPE_NAME){
                    type = NavType.StringType
                    nullable = true
                }
            )
        ){
            QuizPage(
                onTotal = {result ->
                    val route = routeToQuizTotal(result)
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = QuizScreen.QuizSelectLevel.route,
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
        ){backStackEntry ->
            val categoryID = backStackEntry.arguments?.getString(NavigationConst.CATEGORY_ID_NAME)
            val categoryTitle = backStackEntry.arguments?.getString(NavigationConst.CATEGORY_TITLE_NAME)
            val category = Category(
                categoryID?.toInt()?:0,
                categoryTitle?: stringResource(R.string.custom_quiz))
            SelectLevelPage(
                category = category,
                onQuiz = { route->
                    navController.navigate(routeToQuiz(route))
                },

            )
        }
        composable(
            route = QuizScreen.QuizTotal.route
        ){backStackEntry ->
            val result = backStackEntry.arguments?.getString(NavigationConst.RESULT_NAME)
            TotalPage(
                result = result?:"0",
                onHomePage = {
                    navController.navigate(HomeScreen.HomeMain.route){
                        popUpTo(0)
                    }
                }
            )
        }
    }
}