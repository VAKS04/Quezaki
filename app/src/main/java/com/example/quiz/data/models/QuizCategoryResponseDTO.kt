package com.example.quiz.data.models

import com.google.gson.annotations.SerializedName

data class QuizCategoryResponseDTO(
    @SerializedName("response_code") val responseCode:Int,
    @SerializedName("trivia_categories") val triviaCategories : List<QuizCategoryDTO> = emptyList()
)