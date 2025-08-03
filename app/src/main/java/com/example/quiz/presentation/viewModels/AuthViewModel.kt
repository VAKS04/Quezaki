package com.example.quiz.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.domain.usecases.CheckUserCacheUseCase
import com.example.quiz.domain.usecases.ClearUserCacheUseCase
import com.example.quiz.presentation.models.authScreenModels.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val checkUserCacheUseCase: CheckUserCacheUseCase,
    private val clearUserCacheUseCase: ClearUserCacheUseCase
):ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState = _authState.asStateFlow()

    init {
        loadUserPreferences()
    }

    private fun loadUserPreferences() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            checkUserCacheUseCase().collect{cache->
                if (cache == null) {
                    _authState.value = AuthState.Unauthenticated
                } else {
                    _authState.value = AuthState.Authenticated
                }
            }
        }
    }

    fun logOut(){
        viewModelScope.launch {
            clearUserCacheUseCase()
            _authState.value = AuthState.Unauthenticated
        }
    }
}