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

/**
 *
 * @property context Contexto de Android para acceder a DataStore.
 *                   Preferiblemente usar Application context.
 *
 * @see UserSession
 * @see com.example.saborlocalspa.repository.UserRepository
 */
class SessionManager(private val context: Context) {

    companion object {
        /** Clave para almacenar el ID del usuario autenticado */
        private val KEY_USER_ID = stringPreferencesKey("user_id")

        /** Clave para almacenar el email del usuario autenticado */
        private val KEY_USER_EMAIL = stringPreferencesKey("user_email")

        /** Clave para almacenar el nombre del usuario autenticado */
        private val KEY_USER_NAME = stringPreferencesKey("user_name")

        /** Clave para almacenar el token de autenticación JWT */
        private val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")

        /** Clave para el flag de estado de autenticación */
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

        /** Clave para la preferencia "Recordarme" */
        private val KEY_REMEMBER_ME = booleanPreferencesKey("remember_me")

        /** Clave para la preferencia de tema: "light", "dark" o "system" */
        private val KEY_THEME_MODE = stringPreferencesKey("theme_mode")
    }

    /**
     *
     * @param userId Identificador único del usuario (UUID).
     * @param email Correo electrónico del usuario autenticado.
     * @param name Nombre completo del usuario.
     * @param authToken Token JWT de autenticación para autorizar peticiones a la API.
     * @param rememberMe Si es true, la sesión persiste indefinidamente.
     *                   Si es false, se limpia al llamar [clearSession].
     *
     * @see clearSession
     * @see isLoggedIn
     */
    suspend fun saveUserSession(
        userId: String,
        email: String,
        name: String,
        authToken: String,
        rememberMe: Boolean = false
    ) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
            preferences[KEY_USER_EMAIL] = email
            preferences[KEY_USER_NAME] = name
            preferences[KEY_AUTH_TOKEN] = authToken
            preferences[KEY_IS_LOGGED_IN] = true
            preferences[KEY_REMEMBER_ME] = rememberMe
        }
    }

    /**
     *
     * @return Flow que emite true si el usuario está autenticado, false si no.
     */
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_IS_LOGGED_IN] ?: false
    }

    /**
     *
     * @return Flow que emite [UserSession] si hay sesión, null si no.
     *
     * @see UserSession
     */
    val userSession: Flow<UserSession?> = context.dataStore.data.map { preferences ->
        if (preferences[KEY_IS_LOGGED_IN] == true) {
            UserSession(
                userId = preferences[KEY_USER_ID] ?: "",
                email = preferences[KEY_USER_EMAIL] ?: "",
                name = preferences[KEY_USER_NAME] ?: "",
                rememberMe = preferences[KEY_REMEMBER_ME] ?: false
            )
        } else null
    }

    /**
     * @param token Token JWT de autenticación.
     *
     * @see saveUserSession
     * @see getAuthToken
     */
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = token
        }
    }

    /**
     * Obtiene el token de autenticación almacenado.
     *
     * Esta función suspendida recupera el authToken de DataStore.
     * Útil para peticiones manuales a la API que requieran el token.
     *
     * @return El token JWT si existe, null si no hay sesión activa.
     *
     * @see saveUserSession
     */
    suspend fun getAuthToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_AUTH_TOKEN]
        }.first()
    }

    /**
     * @see saveUserSession
     */
    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
            preferences.remove(KEY_USER_EMAIL)
            preferences.remove(KEY_USER_NAME)
            preferences.remove(KEY_AUTH_TOKEN)
            preferences[KEY_IS_LOGGED_IN] = false
            if (preferences[KEY_REMEMBER_ME] != true) {
                preferences.clear()
            }
        }
    }

    /**
     * Guarda la preferencia de tema del usuario.
     *
     * @param mode Modo de tema: "light", "dark" o "system" (sigue el tema del sistema).
     *
     * @see themeMode
     */
    suspend fun saveThemeMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_THEME_MODE] = mode
        }
    }

    /**
     * Flow que emite la preferencia de tema del usuario.
     *
     * Valores posibles:
     * - "light": Modo claro forzado
     * - "dark": Modo oscuro forzado
     * - "system": Sigue la preferencia del sistema (valor por defecto)
     *
     * @return Flow que emite el modo de tema actual.
     */
    val themeMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_THEME_MODE] ?: "system"
    }
}

/**
 *
 * @property userId Identificador único (UUID) del usuario autenticado.
 * @property email Correo electrónico del usuario.
 * @property name Nombre completo del usuario.
 * @property rememberMe Indica si el usuario eligió "Recordarme" en el login.
 *
 * @see SessionManager
 */
data class UserSession(
    val userId: String,
    val email: String,
    val name: String,
    val rememberMe: Boolean
)