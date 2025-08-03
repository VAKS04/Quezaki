package com.example.quiz.data.api

import com.example.quiz.data.models.QuizResponseDTO
import retrofit2.http.Query
import retrofit2.http.GET

const val QUIZ_URL = "https://opentdb.com/"
const val QUIZ_API = "quiz_api"

interface QuizApiService {
    @GET("api.php")
    suspend fun getQuizResponse(
        @Query("amount") amount:Int,
        @Query("category") category: Int? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("type") type: String? = null,
    ): QuizResponseDTO
}