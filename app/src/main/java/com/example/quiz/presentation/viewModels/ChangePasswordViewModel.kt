package com.example.quiz.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.domain.usecases.CheckUserCacheUseCase
import com.example.quiz.domain.usecases.SaveUserCacheUseCase
import com.example.quiz.domain.usecases.UpdateUserUseCase
import com.example.quiz.domain.usecases.ValidateOldPasswordUseCase
import com.example.quiz.domain.usecases.ValidatePasswordUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChangePasswordViewModel(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateOldPasswordUseCase: ValidateOldPasswordUseCase,
    private val checkUserCacheUseCase: CheckUserCacheUseCase,
    private val saveUserCacheUseCase: SaveUserCacheUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
):ViewModel() {

    private val _showAlert = MutableStateFlow<Boolean>(false)
    val showAlert = _showAlert.asStateFlow()

    private val _oldPassword = MutableStateFlow("")
    val oldPassword = _oldPassword.asStateFlow()

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled = _isButtonEnabled.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword = _newPassword.asStateFlow()

    private val _validateNewPasswordMessage = MutableStateFlow<Result<Unit>>(Result.success(Unit))
    val validateNewPasswordMessage = _validateNewPasswordMessage.asStateFlow()

    private val _validateOldPasswordMessage = MutableStateFlow<Result<Unit>>(Result.success(Unit))
    val validateOldPasswordMessage = _validateOldPasswordMessage.asStateFlow()

    private val _resultMessage = MutableStateFlow<Result<Unit>>(Result.success(Unit))
    val resultMessage = _resultMessage.asStateFlow()

    fun updateOldPassword(newValue:String){
        _oldPassword.value = newValue
        _validateOldPasswordMessage.value = validatePasswordUseCase(newValue)
        updateButtonState()
    }

    fun updateShowAlertState(){
        _showAlert.value = !_showAlert.value
    }

    fun updateNewPassword(newValue: String){
        _newPassword.value = newValue
        _validateNewPasswordMessage.value = validatePasswordUseCase(newValue)
        updateButtonState()
    }

    private fun updateButtonState() {
        _isButtonEnabled.value =
            _validateNewPasswordMessage.value.isSuccess &&
                    _validateOldPasswordMessage.value.isSuccess &&
                    _newPassword.value.isNotBlank() &&
                    _oldPassword.value.isNotBlank()
    }

    fun saveChanges() {
        viewModelScope.launch {
            validateOldPasswordUseCase(_oldPassword.value)
                .fold(
                    onSuccess = {
                        updatePassword()
                    },
                    onFailure = { error ->
                        handlePasswordError(error)
                    }
                )
                .also {
                    updateShowAlertState()
                }
        }
    }

    private suspend fun updatePassword() {
        try {
            val oldData = checkUserCacheUseCase().first() ?: throw IllegalStateException("User data not found")
            val newData = oldData.copy(password = _newPassword.value)

            updateUserUseCase(newData)
            saveUserCacheUseCase(newData)

            _resultMessage.value = Result.success(Unit)
            clearPasswordFields()

        } catch (e: Exception) {
            _resultMessage.value = Result.failure(e)
            _oldPassword.value = ""
        }
    }

    private fun handlePasswordError(error: Throwable) {
        _resultMessage.value = Result.failure(error)
        _oldPassword.value = ""
        _isButtonEnabled.value = false
    }

    private fun clearPasswordFields() {
        _oldPassword.value = ""
        _newPassword.value = ""
        _isButtonEnabled.value = false
    }
}