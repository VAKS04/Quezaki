package com.example.quiz.presentation.models.quizScreenModels.quizPageModels


data class QuizQuestionUiState (
    val question:String,
    val type:String,
    val difficulty:String,
    val correctAnswer:String,
    val answers:List<String>,
    val showAnswer: Boolean,
    val selectedItem: String?,
    val isActiveButton:Boolean
)