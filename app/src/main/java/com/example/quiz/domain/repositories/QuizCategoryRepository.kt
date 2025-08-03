package com.example.quiz.domain.repositories

import com.example.quiz.domain.models.QuizCategory

interface QuizCategoryRepository {
    var cachedList:List<QuizCategory>?

    suspend fun fetchQuizCategory():List<QuizCategory>
}