package com.example.quiz.presentation.ui.navigation.routes


sealed class QuizScreen (val route:String){
    object QuizMain: QuizScreen(
        "quiz" +
                "?categoryID={categoryID}" +
                "&amount={amount}" +
                "&difficulty={difficulty}" +
                "&type={type}")
    object QuizSelectLevel: QuizScreen(
        "select_level" +
                "?categoryID={categoryID}" +
                "&categoryTitle={categoryTitle}")
    object QuizTotal: QuizScreen("total&result={result}")
}