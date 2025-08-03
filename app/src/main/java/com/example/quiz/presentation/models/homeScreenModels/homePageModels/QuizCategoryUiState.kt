package com.example.quiz.presentation.models.homeScreenModels.homePageModels

import com.example.quiz.data.models.QuizCategoryDTO


sealed interface QuizCategoryUiState{
    data class Success(val items:List<QuizCategoryDTO>): QuizCategoryUiState
    object Loading: QuizCategoryUiState
    object Error: QuizCategoryUiState
    object NetworkError: QuizCategoryUiState
    object ServerError: QuizCategoryUiState
}