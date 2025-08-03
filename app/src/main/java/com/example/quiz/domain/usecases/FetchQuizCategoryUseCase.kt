package com.example.quiz.domain.usecases

import com.example.quiz.domain.models.QuizCategory
import com.example.quiz.domain.repositories.QuizCategoryRepository

class FetchQuizCategoryUseCase(
    private val repository: QuizCategoryRepository
) {
    suspend operator fun invoke ():List<QuizCategory>{
        return repository.fetchQuizCategory()
    }
}