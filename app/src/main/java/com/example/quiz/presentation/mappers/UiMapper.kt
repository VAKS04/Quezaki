package com.example.quiz.presentation.mappers

import com.example.quiz.domain.models.QuizQuestion
import com.example.quiz.domain.models.QuizResponse
import com.example.quiz.domain.models.User
import com.example.quiz.domain.usecases.CombineAndStirUseCase
import com.example.quiz.presentation.models.quizScreenModels.quizPageModels.QuizQuestionUiState
import com.example.quiz.presentation.models.quizScreenModels.quizPageModels.QuizResponseUiState
import com.example.quiz.presentation.models.UserUiState
import org.apache.commons.text.StringEscapeUtils

fun QuizQuestion.toQuizQuestionUiState(
    combineAndStirUseCase: CombineAndStirUseCase
): QuizQuestionUiState {
    val question = StringEscapeUtils.unescapeHtml4(this.question)
    val correctAnswer = StringEscapeUtils.unescapeHtml4(this.correctAnswer)
    val incorrectAnswers = this.incorrectAnswers.map {
        StringEscapeUtils.unescapeHtml4(it)
    }
    val answers = combineAndStirUseCase(correctAnswer,incorrectAnswers)

    return QuizQuestionUiState(
        question = question,
        type = this.type,
        difficulty = this.difficulty,
        correctAnswer = correctAnswer,
        answers = answers,
        showAnswer = false,
        selectedItem = null,
        isActiveButton = false
    )
}

fun QuizResponse.toQuizResponseUiState(
    combineAndStirUseCase: CombineAndStirUseCase
): QuizResponseUiState {
    return QuizResponseUiState(
        results = results.map {
            it.toQuizQuestionUiState(combineAndStirUseCase)
        }
    )
}

fun User.toUserUiState():UserUiState{
    return UserUiState(
        id = this.id,
        email = this.email,
        username = this.username,
        password = this.password,
        avatarPath = this.avatarPath

    )
}