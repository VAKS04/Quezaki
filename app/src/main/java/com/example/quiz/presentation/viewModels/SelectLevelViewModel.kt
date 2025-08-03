package com.example.quiz.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.quiz.data.models.Category
import com.example.quiz.domain.models.Difficulty
import com.example.quiz.domain.models.NumberOfQuestions
import com.example.quiz.domain.models.TypeAnswer
import com.example.quiz.presentation.models.quizScreenModels.SelectLevelUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectLevelViewModel(
    category: Category?
): ViewModel() {
    private val _quizUiState = MutableStateFlow(SelectLevelUiState())
    val quizUiState : StateFlow<SelectLevelUiState> = _quizUiState.asStateFlow()

    init {
        setCategory(category = category)
    }

    private fun setCategory(category:Category?){
        _quizUiState.value = _quizUiState.value.copy(
            category = category
        )
    }

    fun setDifficulty(difficulty: Difficulty){
        _quizUiState.value = _quizUiState.value.copy(
            difficulty = difficulty
        )
    }

    fun setNumber(number: NumberOfQuestions){
        _quizUiState.value = _quizUiState.value.copy(
            numberOfQuestion = number
        )
    }

    fun setTypeAnswer(typeAnswer: TypeAnswer){
        _quizUiState.value = _quizUiState.value.copy(
            typeAnswer = typeAnswer
        )
    }
}