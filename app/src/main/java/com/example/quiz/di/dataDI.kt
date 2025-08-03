package com.example.quiz.di

import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.quiz.data.database.DB_NAME
import com.example.quiz.data.database.QuezakiDatabase
import com.example.quiz.data.api.CATEGORY_API
import com.example.quiz.data.api.CATEGORY_URL
import com.example.quiz.data.api.QUIZ_API
import com.example.quiz.data.api.QUIZ_URL
import com.example.quiz.data.api.QuizApiService
import com.example.quiz.data.api.QuizCategoryApiService
import com.example.quiz.data.repositories.QuizCategoryRepositoryImpl
import com.example.quiz.data.repositories.QuizRepositoryImpl
import com.example.quiz.data.database.UserDao
import com.example.quiz.data.repositories.UserPreferencesRepositoryImpl
import com.example.quiz.domain.repositories.QuizCategoryRepository
import com.example.quiz.domain.repositories.QuizRepository
import com.example.quiz.domain.repositories.UserPreferencesRepository
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<UserDao> {
        get<QuezakiDatabase>().userDao()
    }

    single<QuezakiDatabase> {
        Room.databaseBuilder(
            get(),
            QuezakiDatabase::class.java,
            DB_NAME
        ).build()
    }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                val file = androidContext().dataStoreFile("dataStore/user_pref.preferences_pb")
                file.parentFile?.mkdirs()
                file
            }
        )
    }

    single<Retrofit>(named(CATEGORY_API)){
        Retrofit.Builder()
            .baseUrl(CATEGORY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Retrofit>(named(QUIZ_API)) {
        Retrofit.Builder()
            .baseUrl(QUIZ_URL)
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder()
                .create()))
            .build()
    }

    single<QuizCategoryApiService>{
        get<Retrofit>(named(CATEGORY_API)).create(QuizCategoryApiService::class.java)
    }

    single<QuizApiService> {
        get<Retrofit>(named(QUIZ_API)).create(QuizApiService::class.java)
    }
}