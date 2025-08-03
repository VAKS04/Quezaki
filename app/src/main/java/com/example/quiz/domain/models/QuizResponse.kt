package com.example.quiz.domain.models

data class QuizResponse(
    val responseCode:Int,
    val results : List<QuizQuestion>,
)