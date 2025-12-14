package com.example.saborlocalspa.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "session_preferences"
)

class SessionManager(private val context: Context) {

    companion object {
        private val KEY_USER_ID = stringPreferencesKey("user_id")
        private val KEY_USER_EMAIL = stringPreferencesKey("user_email")
        private val KEY_USER_NAME = stringPreferencesKey("user_name")
        private val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val KEY_REMEMBER_ME = booleanPreferencesKey("remember_me")
        private val KEY_THEME_MODE = stringPreferencesKey("theme_mode")

        // NUEVO: rol del usuario
        private val KEY_USER_ROLE = stringPreferencesKey("user_role")
    }

    suspend fun saveUserSession(
        userId: String,
        email: String,
        name: String,
        authToken: String,
        rememberMe: Boolean = false,
        role: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
            preferences[KEY_USER_EMAIL] = email
            preferences[KEY_USER_NAME] = name
            preferences[KEY_AUTH_TOKEN] = authToken
            preferences[KEY_IS_LOGGED_IN] = true
            preferences[KEY_REMEMBER_ME] = rememberMe
            preferences[KEY_USER_ROLE] = role
        }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_IS_LOGGED_IN] ?: false
    }

    val userSession: Flow<UserSession?> = context.dataStore.data.map { preferences ->
        if (preferences[KEY_IS_LOGGED_IN] == true) {
            UserSession(
                userId = preferences[KEY_USER_ID] ?: "",
                email = preferences[KEY_USER_EMAIL] ?: "",
                name = preferences[KEY_USER_NAME] ?: "",
                rememberMe = preferences[KEY_REMEMBER_ME] ?: false,
                role = preferences[KEY_USER_ROLE] ?: "CLIENTE"
            )
        } else null
    }

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = token
        }
    }

    suspend fun getAuthToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_AUTH_TOKEN]
        }.first()
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
            preferences.remove(KEY_USER_EMAIL)
            preferences.remove(KEY_USER_NAME)
            preferences.remove(KEY_AUTH_TOKEN)
            preferences.remove(KEY_USER_ROLE)
            preferences[KEY_IS_LOGGED_IN] = false
            if (preferences[KEY_REMEMBER_ME] != true) {
                preferences.clear()
            }
        }
    }

    suspend fun saveThemeMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_THEME_MODE] = mode
        }
    }

    val themeMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_THEME_MODE] ?: "system"
    }
}

data class UserSession(
    val userId: String,
    val email: String,
    val name: String,
    val rememberMe: Boolean,
    val role: String
)
