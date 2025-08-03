package com.example.quiz.data.models

import com.google.gson.annotations.SerializedName

data class QuizQuestionDTO(
    val question:String,
    val type:String,
    val difficulty: String,
    @SerializedName("correct_answer") val correctAnswer:String,
    @SerializedName("incorrect_answers") val incorrectAnswers:List<String>
)