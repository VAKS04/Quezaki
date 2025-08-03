package com.example.quiz.data.mappers

import com.example.quiz.data.models.QuizCategoryDTO
import com.example.quiz.data.models.QuizCategoryResponseDTO
import com.example.quiz.data.models.QuizDTO
import com.example.quiz.data.models.QuizQuestionDTO
import com.example.quiz.data.models.QuizResponseDTO
import com.example.quiz.domain.models.Quiz
import com.example.quiz.domain.models.QuizCategory
import com.example.quiz.domain.models.QuizQuestion
import com.example.quiz.domain.models.QuizResponse

fun QuizQuestionDTO.toQuizQuestion(): QuizQuestion {
    return QuizQuestion(
        question = question,
        type = type,
        difficulty = difficulty,
        correctAnswer = correctAnswer,
        incorrectAnswers = incorrectAnswers,
    )
}

fun QuizResponseDTO.toQuizResponse(): QuizResponse {
    return QuizResponse(
        responseCode = responseCode,
        results = results.map {
            it.toQuizQuestion()
        }
    )
}

fun QuizQuestion.toQuestionDTO(): QuizQuestionDTO {
    return QuizQuestionDTO(
        question = question,
        type = type,
        difficulty = difficulty,
        correctAnswer = correctAnswer,
        incorrectAnswers = incorrectAnswers
    )
}

fun QuizResponse.toQuizResponseDTO(): QuizResponseDTO {
    return QuizResponseDTO(
        responseCode = responseCode,
        results = results.map {
            it.toQuestionDTO()
        }
    )
}



fun Quiz.toQuizDTO():QuizDTO{
    return QuizDTO(
        amount = amount,
        category = category,
        difficulty = difficulty,
        type = type
    )
}

fun QuizDTO.toQuiz(): Quiz {
    return Quiz(
        amount = amount,
        category = category,
        difficulty = difficulty,
        type = type
    )
}

fun QuizCategory.toQuizCategoryDTO():QuizCategoryDTO{
    return QuizCategoryDTO(
        id = id,
        name = name
    )
}



fun QuizCategoryDTO.toQuizCategory(): QuizCategory {
    return QuizCategory(
        id = id,
        name = name
    )
}