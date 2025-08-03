package com.example.quiz.data.repositories

import com.example.quiz.data.mappers.toQuizResponse
import com.example.quiz.data.api.QuizApiService
import com.example.quiz.domain.models.QuizResponse
import com.example.quiz.domain.repositories.QuizRepository


class QuizRepositoryImpl(
    private val apiService: QuizApiService
):QuizRepository {
    override suspend fun fetchQuiz(
        amount: Int,
        category: Int?,
        difficulty: String?,
        type: String?
    ): QuizResponse {
        return apiService.getQuizResponse(
            amount = amount,
            category = category,
            difficulty = difficulty,
            type = type
        ).toQuizResponse()
    }
}