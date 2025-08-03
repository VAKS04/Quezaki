package com.example.quiz.data.models

import com.example.quiz.domain.models.Difficulty
import com.example.quiz.domain.models.TypeAnswer

data class QuizDTO(
    val amount:Int,
    val category: Int? = null,
    val difficulty: Difficulty? = null,
    val type: TypeAnswer? = null,
)