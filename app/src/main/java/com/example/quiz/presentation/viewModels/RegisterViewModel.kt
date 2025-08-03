package com.example.quiz.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.domain.exceptions.UserExistException
import com.example.quiz.domain.usecases.RegisterUserUseCase
import com.example.quiz.domain.usecases.ValidateEmailUseCase
import com.example.quiz.domain.usecases.ValidatePasswordUseCase
import com.example.quiz.domain.usecases.ValidateUsernameUseCase
import com.example.quiz.presentation.models.authScreenModels.registerPageModels.RegisterDataUiState
import com.example.quiz.presentation.models.authScreenModels.registerPageModels.RegisterPageUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase
) : ViewModel() {
    private val _registerDataUiState = MutableStateFlow(RegisterDataUiState())
    val registerDataUiState = _registerDataUiState.asStateFlow()

    private val _registerPageUiState = MutableStateFlow<RegisterPageUiState>(RegisterPageUiState.Idle)
    val registerPageUiState = _registerPageUiState.asStateFlow()

    private val _usernameValidation = MutableStateFlow(Result.success(Unit))
    private val _emailValidation = MutableStateFlow(Result.success(Unit))
    private val _passwordValidation = MutableStateFlow(Result.success(Unit))

    val usernameValidation: StateFlow<Result<Unit>> = _usernameValidation.asStateFlow()
    val emailValidation: StateFlow<Result<Unit>> = _emailValidation.asStateFlow()
    val passwordValidation: StateFlow<Result<Unit>> = _passwordValidation.asStateFlow()

    fun updateUsername(newValue: String) {
        _registerDataUiState.value = _registerDataUiState.value.copy(
            username = newValue
        )
        _usernameValidation.value = validateUsernameUseCase(newValue)
        updateButtonState()
    }

    fun updateEmail(newValue: String) {
        _registerDataUiState.value = _registerDataUiState.value.copy(
            email = newValue
        )
        _emailValidation.value = validateEmailUseCase(newValue)
        updateButtonState()
    }

    fun updatePassword(newValue: String) {
        _registerDataUiState.value = _registerDataUiState.value.copy(
            password = newValue
        )
        _passwordValidation.value = validatePasswordUseCase(newValue)
        updateButtonState()
    }

    private fun updateButtonState() {
        _registerDataUiState.value = _registerDataUiState.value.copy(
            isButtonEnabled = (
                    _usernameValidation.value.isSuccess &&
                            _emailValidation.value.isSuccess &&
                            _passwordValidation.value.isSuccess &&
                            _registerDataUiState.value.username.isNotBlank() &&
                            _registerDataUiState.value.email.isNotBlank() &&
                            _registerDataUiState.value.password.isNotBlank()
                    )
        )
    }

    fun updateToIdle(){
        _registerPageUiState.value = RegisterPageUiState.Idle
    }

    fun registerUser() {
        viewModelScope.launch {
            _registerPageUiState.value = RegisterPageUiState.Idle
            _registerPageUiState.value = try {
                registerUserUseCase(
                    username = _registerDataUiState.value.username,
                    email = _registerDataUiState.value.email,
                    password = _registerDataUiState.value.password
                )
                RegisterPageUiState.Success
            }catch (e:UserExistException){
                RegisterPageUiState.UserExist
            }catch (e:Exception){
                RegisterPageUiState.Error(e.message?:"")
            }
        }
    }
}