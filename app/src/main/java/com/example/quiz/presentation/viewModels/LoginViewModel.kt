package com.example.quiz.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.domain.exceptions.UserNotFoundException
import com.example.quiz.domain.usecases.AuthorizationUseCase
import com.example.quiz.domain.usecases.SaveUserCacheUseCase
import com.example.quiz.domain.usecases.ValidateEmailUseCase
import com.example.quiz.domain.usecases.ValidatePasswordUseCase
import com.example.quiz.presentation.models.authScreenModels.loginPageModels.LoginDataUiState
import com.example.quiz.presentation.models.authScreenModels.loginPageModels.LoginPageUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val authorizationUseCase: AuthorizationUseCase,
    private val saveUserDataUseCase: SaveUserCacheUseCase
): ViewModel() {
    private val _loginPageUiState = MutableStateFlow<LoginPageUiState>(LoginPageUiState.Idle)
    val loginPageUiState = _loginPageUiState.asStateFlow()

    private val _loginDataUiState = MutableStateFlow(LoginDataUiState())
    val loginDataUiState = _loginDataUiState.asStateFlow()

    private val _emailValidation = MutableStateFlow(Result.success(Unit))
    private val _passwordValidation = MutableStateFlow(Result.success(Unit))

    val emailValidation = _emailValidation.asStateFlow()
    val passwordValidation = _passwordValidation.asStateFlow()

    fun updateEmail(newValue:String){
        _loginDataUiState.value = _loginDataUiState.value.copy(
            email = newValue
        )
        _emailValidation.value = validateEmailUseCase(newValue)
        updateButtonState()
    }

    fun updatePassword(newValue: String){
        _loginDataUiState.value = _loginDataUiState.value.copy(
            password = newValue
        )
        _passwordValidation.value = validatePasswordUseCase(newValue)
        updateButtonState()
    }

    private fun updateButtonState(){
        _loginDataUiState.value = _loginDataUiState.value.copy(
            isButtonEnabled = (
                    _emailValidation.value.isSuccess &&
                            _passwordValidation.value.isSuccess &&
                            _loginDataUiState.value.email.isNotBlank() &&
                            _loginDataUiState.value.password.isNotBlank()
                    )
        )
    }


    fun loginUser() {
        viewModelScope.launch {
            _loginPageUiState.value = LoginPageUiState.Idle
            _loginPageUiState.value = try {
                val user = authorizationUseCase(
                    email = _loginDataUiState.value.email,
                    password = _loginDataUiState.value.password)
                saveUserDataUseCase(user)
                LoginPageUiState.Success
            }catch (e:UserNotFoundException){
                LoginPageUiState.UserNotFound
            }catch (e:Exception){
                LoginPageUiState.Error(e.message?:"")
            }
        }
    }

    fun updateToIdle(){
        _loginPageUiState.value = LoginPageUiState.Idle
    }
}