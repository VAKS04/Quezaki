package com.example.quiz.domain.repositories

import com.example.quiz.domain.models.QuizResponse

interface QuizRepository {
    suspend fun  fetchQuiz(
        amount: Int,
        category:Int?,
        difficulty: String?,
        type: String?
    ): QuizResponse
}