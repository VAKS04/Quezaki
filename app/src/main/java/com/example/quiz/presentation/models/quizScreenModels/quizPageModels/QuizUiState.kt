package com.example.quiz.presentation.models.quizScreenModels.quizPageModels


sealed class QuizUiState{
    object Loading: QuizUiState()
    object ErrorResponseCode: QuizUiState()
    object Error: QuizUiState()
    object ServerError: QuizUiState()
    object NetworkError: QuizUiState()
    object Success : QuizUiState()
}