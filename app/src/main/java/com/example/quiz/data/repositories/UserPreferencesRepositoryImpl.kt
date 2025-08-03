package com.example.quiz.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.quiz.domain.models.User
import com.example.quiz.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
):UserPreferencesRepository{

    private companion object{
        val ID = intPreferencesKey("id")
        val EMAIL = stringPreferencesKey("email")
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
    }

    override suspend fun editUserData(user: User) {
        dataStore.edit {preferences ->
            preferences[ID] = user.id
            preferences[EMAIL] = user.email
            preferences[USERNAME] = user.username
            preferences[PASSWORD] = user.password
        }
    }

    override fun readUsername(): Flow<String?> {
        return dataStore.data.map{preferences->
            val username = preferences[USERNAME]
            username
        }
    }

    override fun readPassword(): Flow<String?> {
        return dataStore.data.map { preferences->
            val password = preferences[PASSWORD]
            password
        }
    }

    override fun readUserData(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val id = preferences[ID]
            val email = preferences[EMAIL].orEmpty()
            val username = preferences[USERNAME].orEmpty()
            val password = preferences[PASSWORD].orEmpty()

            if (id == null
                || email.isBlank()
                || username.isBlank()
                || password.isBlank()
                ) { null
            } else {
                User(id, email, username, password)
            }
        }
    }


    override suspend fun clearUserData() {
        dataStore.edit { it.clear() }
    }
}