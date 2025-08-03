package com.example.quiz.domain.models

data class Quiz(
    val amount:Int,
    val category: Int? = null,
    val difficulty: Difficulty? = null,
    val type: TypeAnswer? = null,
)