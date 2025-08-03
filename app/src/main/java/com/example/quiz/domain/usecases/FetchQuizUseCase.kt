package com.example.quiz.domain.usecases


import com.example.quiz.domain.models.QuizResponse
import com.example.quiz.domain.repositories.QuizRepository

class FetchQuizUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke (
        amount:Int,
        category:Int?,
        difficulty: String?,
        type: String?
    ): QuizResponse {
        return repository.fetchQuiz(
            amount = amount,
            category = category,
            difficulty = difficulty,
            type = type
        )
    }
}