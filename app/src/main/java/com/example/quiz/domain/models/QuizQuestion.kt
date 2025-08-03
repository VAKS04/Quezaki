package com.example.quiz.domain.models


data class QuizQuestion(
    val question:String,
    val type:String,
    val difficulty:String,
    val correctAnswer:String,
    val incorrectAnswers:List<String>,
)