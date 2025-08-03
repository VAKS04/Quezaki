package com.example.quiz.presentation.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.HttpException
 import com.example.quiz.domain.usecases.CombineAndStirUseCase
import com.example.quiz.domain.usecases.FetchQuizUseCase
import com.example.quiz.presentation.mappers.toQuizQuestionUiState
import com.example.quiz.presentation.models.quizScreenModels.quizPageModels.QuizQuestionUiState
import com.example.quiz.presentation.models.quizScreenModels.quizPageModels.QuizUiState
import com.example.quiz.presentation.ui.navigation.NavigationConst
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class QuizViewModel(
    private val fetchQuizUseCase: FetchQuizUseCase,
    private val combineAndStirUseCase: CombineAndStirUseCase,
    savedStateHandle: SavedStateHandle,
):ViewModel() {
    private val _quizUiState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)
    private val _quizList = MutableStateFlow<List<QuizQuestionUiState>>(emptyList())

    private val _currentQuestionIndex = MutableStateFlow(0)
    private val _countCorrectAnswer = MutableStateFlow(0)

    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()
    val quizUiState: StateFlow<QuizUiState> = _quizUiState.asStateFlow()
    val countCorrectAnswer = _countCorrectAnswer.asStateFlow()


    val categoryID = savedStateHandle.get<String?>(
        NavigationConst.CATEGORY_ID_NAME,
    )
    val difficulty = savedStateHandle.get<String?>(
        NavigationConst.DIFFICULTY_NAME
    )
    val amount = savedStateHandle.get<String>(
        NavigationConst.AMOUNT_NAME
    )
    val type = savedStateHandle.get<String?>(
        NavigationConst.TYPE_NAME
    )

    init {
        loadQuiz()
    }

    fun updateSelectItem(item:String){
        val currentIndex = _currentQuestionIndex.value
        _quizList.update { currentList->
            currentList.mapIndexed { index, quizQuestionUiState ->
                if (index == currentIndex){
                    quizQuestionUiState.copy(
                        selectedItem = item
                    )
                }else{
                    quizQuestionUiState
                }
            }
        }
    }

    fun updateButtonState(){
        val currentIndex = _currentQuestionIndex.value
        _quizList.update { currentList->
            currentList.mapIndexed{index, quizQuestionUiState ->
                if(index == currentIndex && quizQuestionUiState.selectedItem != null){
                    quizQuestionUiState.copy(
                        isActiveButton = true
                    )
                }else{
                    quizQuestionUiState
                }
            }
        }
    }

    fun updateCountCorrectAnswer(isCorrect:Boolean){
        if (isCorrect) _countCorrectAnswer.value = _countCorrectAnswer.value.inc()
    }

    fun updateShowAnswer(show:Boolean){
        val currentIndex = _currentQuestionIndex.value
        _quizList.update { currentList->
            currentList.mapIndexed{index, quizQuestionUiState ->
                if (index == currentIndex){
                    quizQuestionUiState.copy(
                        showAnswer = show
                    )
                }else{
                    quizQuestionUiState
                }
            }
        }
    }

    private fun loadQuiz(){
        viewModelScope.launch {
            _quizUiState.value = QuizUiState.Loading
            _quizUiState.value = try {
                val result = fetchQuizUseCase.invoke(
                    category = categoryID?.toIntOrNull(),
                    difficulty = difficulty,
                    amount = amount?.toInt()?:10,
                    type = type
                )
                if (result.responseCode == 0){
                    _quizList.value = result.results.map {
                        it.toQuizQuestionUiState(combineAndStirUseCase)
                    }
                    _currentQuestionIndex.value = 0
                    QuizUiState.Success
                }else{
                    QuizUiState.ErrorResponseCode
                }
            }catch (e:IOException){
                QuizUiState.NetworkError
            }catch (e:HttpException){
                QuizUiState.ServerError
            }catch (e:Exception){
                QuizUiState.Error
            }

        }
    }

    fun moveToNextQuestion() {
        _currentQuestionIndex.update { current ->
            if (current < _quizList.value.lastIndex) current + 1 else current
        }
    }

    val currentQuestion: StateFlow<QuizQuestionUiState?> = combine(
        _quizList,          // StateFlow<List<QuizQuestionUiState>>
        _currentQuestionIndex // StateFlow<Int>
    ) { list, index ->
        list.getOrNull(index) // Получаем вопрос по текущему индексу
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun refresh(){
        loadQuiz()
    }
}