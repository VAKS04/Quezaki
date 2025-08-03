package com.example.quiz.data.repositories

import com.example.quiz.data.api.QuizCategoryApiService
import com.example.quiz.data.mappers.toQuizCategory
import com.example.quiz.domain.models.QuizCategory
import com.example.quiz.domain.repositories.QuizCategoryRepository

class QuizCategoryRepositoryImpl(
    private val apiService: QuizCategoryApiService
):QuizCategoryRepository {
    override var cachedList: List<QuizCategory>? = null

    override suspend fun fetchQuizCategory(): List<QuizCategory> {
        return cachedList?: apiService.getQuizCategory().triviaCategories.map {
            it.toQuizCategory()
        }.also {
            cachedList = it
        }
    }
}