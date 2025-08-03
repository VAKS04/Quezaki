package com.example.quiz.di

import com.example.quiz.domain.usecases.AuthorizationUseCase
import com.example.quiz.domain.usecases.CheckUserCacheUseCase
import com.example.quiz.domain.usecases.ClearUserCacheUseCase
import com.example.quiz.domain.usecases.CombineAndStirUseCase
import com.example.quiz.domain.usecases.FetchQuizCategoryUseCase
import com.example.quiz.domain.usecases.FetchQuizUseCase
import com.example.quiz.domain.usecases.IsExistUserUseCase
import com.example.quiz.domain.usecases.ReadPasswordFromCacheUseCase
import com.example.quiz.domain.usecases.ReadUsernameFromCacheUseCase
import com.example.quiz.domain.usecases.RegisterUserUseCase
import com.example.quiz.domain.usecases.SaveUserCacheUseCase
import com.example.quiz.domain.usecases.UpdateUserUseCase
import com.example.quiz.domain.usecases.ValidateEmailUseCase
import com.example.quiz.domain.usecases.ValidateOldPasswordUseCase
import com.example.quiz.domain.usecases.ValidatePasswordUseCase
import com.example.quiz.domain.usecases.ValidateUsernameUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single<RegisterUserUseCase> {
        RegisterUserUseCase(
            repository = get(),
            isExistUserUseCase = get()
        )
    }

    single<UpdateUserUseCase>{
        UpdateUserUseCase(repository = get())
    }

    single<AuthorizationUseCase> {
        AuthorizationUseCase(repository = get())
    }

    single<IsExistUserUseCase> {
        IsExistUserUseCase(repository = get())
    }

    single<ValidateEmailUseCase> {
        ValidateEmailUseCase()
    }

    single<ValidateUsernameUseCase> {
        ValidateUsernameUseCase()
    }

    single<ValidatePasswordUseCase> {
        ValidatePasswordUseCase()
    }

    single<CheckUserCacheUseCase> {
        CheckUserCacheUseCase(repository = get())
    }

    single<SaveUserCacheUseCase> {
        SaveUserCacheUseCase(repository = get())
    }

    single<FetchQuizCategoryUseCase> {
        FetchQuizCategoryUseCase(repository = get())
    }

    single<FetchQuizUseCase> {
        FetchQuizUseCase(repository = get())
    }

    single<CombineAndStirUseCase> {
        CombineAndStirUseCase()
    }

    single<ClearUserCacheUseCase> {
        ClearUserCacheUseCase(repository = get())
    }

    single<ReadUsernameFromCacheUseCase>{
        ReadUsernameFromCacheUseCase(repository = get())
    }

    single<ReadPasswordFromCacheUseCase>{
        ReadPasswordFromCacheUseCase(
            repository = get()
        )
    }

    single<ValidateOldPasswordUseCase> {
        ValidateOldPasswordUseCase(readPasswordFromCacheUseCase = get())
    }
}