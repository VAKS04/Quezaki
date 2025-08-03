package com.example.quiz.data.models

import com.google.gson.annotations.SerializedName

data class QuizResponseDTO(
    @SerializedName("response_code") val responseCode:Int,
    val results:List<QuizQuestionDTO> = emptyList(),
)