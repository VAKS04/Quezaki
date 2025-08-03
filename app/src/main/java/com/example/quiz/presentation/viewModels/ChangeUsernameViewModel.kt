package com.example.quiz.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.domain.usecases.CheckUserCacheUseCase
import com.example.quiz.domain.usecases.ReadUsernameFromCacheUseCase
import com.example.quiz.domain.usecases.SaveUserCacheUseCase
import com.example.quiz.domain.usecases.UpdateUserUseCase
import com.example.quiz.domain.usecases.ValidateUsernameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChangeUsernameViewModel(
    private val readUsernameFromCacheUseCase: ReadUsernameFromCacheUseCase,
    private val saveUserCacheUseCase: SaveUserCacheUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val checkUserCacheUseCase: CheckUserCacheUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
):ViewModel() {
    private val _showAlert = MutableStateFlow<Boolean>(false)
    val showAlert = _showAlert.asStateFlow()

    private val _isButtonEnabled = MutableStateFlow(true)
    val isButtonEnabled = _isButtonEnabled.asStateFlow()

    private val _validateExceptionMessage = MutableStateFlow<Result<Unit>>(Result.success(Unit))
    val validateExceptionMessage = _validateExceptionMessage.asStateFlow()

    private val _changeUsernameResult = MutableStateFlow<Result<Unit>>(Result.success(Unit))
    val changeUsernameResult = _changeUsernameResult.asStateFlow()

    private val _username = MutableStateFlow<String>("")
    val username = _username.asStateFlow()


    init {
        loadUsername()
    }

    private fun loadUsername(){
        viewModelScope.launch {
            readUsernameFromCacheUseCase().collect{username->
                _username.value = username!!
            }
        }
    }

    fun updateUsername(newValue:String){
        _username.value = newValue
        _validateExceptionMessage.value = validateUsernameUseCase(newValue)
        updateButtonState()
    }

    private fun updateButtonState() {
        _isButtonEnabled.value = _validateExceptionMessage.value.isSuccess &&
                _username.value.isNotBlank()
    }

    fun updateShowAlertState(){
        _showAlert.value = !_showAlert.value
    }

    fun saveChanges(){
        viewModelScope.launch {
            val oldUserData = checkUserCacheUseCase().first()
            val newUserData = oldUserData!!.copy(
                username = _username.value
            )
            _changeUsernameResult.value = updateUserUseCase(newUserData)
            saveUserCacheUseCase(newUserData)
            updateShowAlertState()
        }
    }
}