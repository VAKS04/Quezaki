package com.example.quiz.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.data.mappers.toQuizCategoryDTO
import com.example.quiz.data.models.QuizCategoryDTO
import com.example.quiz.domain.usecases.FetchQuizCategoryUseCase
import com.example.quiz.domain.usecases.ReadUsernameFromCacheUseCase
import com.example.quiz.presentation.models.homeScreenModels.homePageModels.QuizCategoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class HomeViewModel(
    private val fetchQuizCategoryUseCase: FetchQuizCategoryUseCase,
    private val readUsernameFromCacheUseCase: ReadUsernameFromCacheUseCase
) : ViewModel() {

    private val _quizPanelField = MutableStateFlow("")
    val quizPanelField = _quizPanelField.asStateFlow()

    private val _username = MutableStateFlow<String>("")
    val username = _username.asStateFlow()

    private val _filterCategoryList = MutableStateFlow<List<QuizCategoryDTO>>(emptyList())
    private val _categoryList = MutableStateFlow<List<QuizCategoryDTO>>(emptyList())

    private val _quizCategoryUiState = MutableStateFlow<QuizCategoryUiState>(QuizCategoryUiState.Loading)
    val quizCategoryUiState = _quizCategoryUiState.asStateFlow()

    init {
        loadUsername()
        loadQuizCategory()
    }

    private fun loadUsername(){
        viewModelScope.launch {
            readUsernameFromCacheUseCase().collect{username->
                if (username!=null){
                    _username.value = username
                }
            }
        }
    }

    private fun loadQuizCategory(){
        viewModelScope.launch {
            _quizCategoryUiState.value = QuizCategoryUiState.Loading
            _quizCategoryUiState.value = try {
                val resultList = fetchQuizCategoryUseCase().map{
                    it.toQuizCategoryDTO()
                }
                _categoryList.value = resultList
                QuizCategoryUiState.Success(resultList)
            }catch (e:IOException){
                QuizCategoryUiState.NetworkError
            }catch (e:HttpException){
                QuizCategoryUiState.ServerError
            }
        }
    }

    private fun filterItems(key:String){
        _filterCategoryList.value = _categoryList.value.filter {
            key.lowercase() in it.name.lowercase()
        }
    }

    fun changeValue(newTextValue:String){
        _quizPanelField.value = newTextValue
    }

    fun updateQuizCategoryUiState(){
        viewModelScope.launch {
            _quizCategoryUiState.value = QuizCategoryUiState.Success(
                if (_quizPanelField.value.isBlank()) {
                    _categoryList.value
                } else {
                    filterItems(_quizPanelField.value)
                    _filterCategoryList.value
                }
            )
        }
    }

    fun refresh(){
        loadUsername()
        loadQuizCategory()
    }
}
