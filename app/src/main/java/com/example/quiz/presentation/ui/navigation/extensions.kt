package com.example.quiz.presentation.ui.navigation

import android.content.Context
import com.example.quiz.R
import com.example.quiz.presentation.models.quizScreenModels.SelectLevelUiState

fun routeToQuiz(state: SelectLevelUiState):String {
    val route = buildString {
        append("quiz")
        append("?categoryID=${state.category?.id ?: ""}")
        append("&amount=${state.numberOfQuestion.value}")
        append("&difficulty=${state.difficulty.value}")
        append("&type=${state.typeAnswer.value}")
    }
    return route
}

fun routeToSelectLevel(context: Context,id:Int?,title:String?):String{
    val route = buildString {
        append("select_level")
        append("?categoryID=${id?:0}")
        append("&categoryTitle=${title?: context.getString(R.string.custom_quiz)}")
    }
    return route
}

fun routeToQuizTotal(score:Int):String{
    val route = buildString {
        append("total")
        append("&result=${score}")
    }
    return route
}