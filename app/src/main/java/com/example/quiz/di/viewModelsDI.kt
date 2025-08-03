package com.example.quiz.di

import androidx.lifecycle.SavedStateHandle
import com.example.quiz.data.models.Category
import com.example.quiz.presentation.viewModels.AuthViewModel
import com.example.quiz.presentation.viewModels.ChangePasswordViewModel
import com.example.quiz.presentation.viewModels.ChangeUsernameViewModel
import com.example.quiz.presentation.viewModels.HomeViewModel
import com.example.quiz.presentation.viewModels.LoginViewModel
import com.example.quiz.presentation.viewModels.QuizViewModel
import com.example.quiz.presentation.viewModels.RegisterViewModel
import com.example.quiz.presentation.viewModels.SelectLevelViewModel
import com.example.quiz.presentation.viewModels.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel<RegisterViewModel>{
        RegisterViewModel(
            registerUserUseCase = get(),
            validateEmailUseCase = get(),
            validatePasswordUseCase = get(),
            validateUsernameUseCase = get()
        )
    }

    viewModel<LoginViewModel> {
        LoginViewModel(
            validatePasswordUseCase = get(),
            validateEmailUseCase = get(),
            authorizationUseCase = get(),
            saveUserDataUseCase = get()
        )
    }

    viewModel<HomeViewModel> {
        HomeViewModel(
            fetchQuizCategoryUseCase = get(),
            readUsernameFromCacheUseCase = get()
        )
    }

    viewModel<SelectLevelViewModel>{ (category: Category?)->
        SelectLevelViewModel(
            category = category
        )
    }

    viewModel<QuizViewModel> { (savedStateHandle: SavedStateHandle)->
        QuizViewModel(
            fetchQuizUseCase = get(),
            combineAndStirUseCase = get(),
            savedStateHandle = savedStateHandle)
    }
    viewModel<SettingsViewModel>{
        SettingsViewModel()
    }
    viewModel<AuthViewModel> {
        AuthViewModel(
            checkUserCacheUseCase = get(),
            clearUserCacheUseCase = get())
    }
    viewModel<ChangeUsernameViewModel>{
        ChangeUsernameViewModel(
            readUsernameFromCacheUseCase = get(),
            checkUserCacheUseCase = get(),
            validateUsernameUseCase = get(),
            saveUserCacheUseCase = get(),
            updateUserUseCase = get()
        )
    }
    viewModel<ChangePasswordViewModel> {
        ChangePasswordViewModel(
            validatePasswordUseCase = get(),
            validateOldPasswordUseCase = get(),
            checkUserCacheUseCase = get(),
            saveUserCacheUseCase = get(),
            updateUserUseCase = get()
        )
    }
}