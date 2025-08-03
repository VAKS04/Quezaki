package com.example.quiz.presentation.models.quizScreenModels

import com.example.quiz.data.models.Category
import com.example.quiz.domain.models.Difficulty
import com.example.quiz.domain.models.NumberOfQuestions
import com.example.quiz.domain.models.TypeAnswer

data class SelectLevelUiState(
    val category:Category? = null,
    val difficulty: Difficulty = Difficulty.MIXED,
    val numberOfQuestion: NumberOfQuestions = NumberOfQuestions.TEN,
    val typeAnswer: TypeAnswer = TypeAnswer.MIXED
)