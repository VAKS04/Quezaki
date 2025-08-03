package com.example.quiz.di

import com.example.quiz.data.repositories.QuizCategoryRepositoryImpl
import com.example.quiz.data.repositories.QuizRepositoryImpl
import com.example.quiz.data.repositories.UserPreferencesRepositoryImpl
import com.example.quiz.data.repositories.UserRepositoryImpl
import com.example.quiz.domain.repositories.QuizCategoryRepository
import com.example.quiz.domain.repositories.QuizRepository
import com.example.quiz.domain.repositories.UserPreferencesRepository
import com.example.quiz.domain.repositories.UserRepository
import org.koin.dsl.module

val repositoriesModule = module{
    single<UserRepository>{
        UserRepositoryImpl(userDao = get())
    }

    single<QuizRepository> {
        QuizRepositoryImpl(apiService = get())
    }

    single<UserPreferencesRepository> {
        UserPreferencesRepositoryImpl(dataStore = get())
    }

    single<QuizCategoryRepository> {
        QuizCategoryRepositoryImpl(
            apiService = get()
        )
    }
}