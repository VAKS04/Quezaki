package com.example.quiz.data.api

import com.example.quiz.data.models.QuizCategoryResponseDTO
import retrofit2.http.GET

const val CATEGORY_URL = "https://opentdb.com/"
const val CATEGORY_API = "category_api"

interface QuizCategoryApiService {
    @GET("api_category.php")
    suspend fun getQuizCategory():QuizCategoryResponseDTO
}