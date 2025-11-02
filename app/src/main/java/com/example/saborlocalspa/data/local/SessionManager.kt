package com.example.saborlocalspa.data.local

// Importa las librerías necesarias para DataStore y corrutinas
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * SessionManager: Clase para gestionar la sesión del usuario,
 * nos permite guardar, obtener y borrar el token JWT de forma segura usando DataStore.
 */
class SessionManager(private val context: Context) {

    companion object {
        // Define una propiedad delegada para crear la instancia de DataStore de preferencias con nombre "session_prefs"
        private val Context.dataStore by preferencesDataStore(name = "session_prefs")

        // Define la clave bajo la cual se guardará el token en DataStore
        private val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
    }

    /**
     * Guarda el token de autenticación en DataStore.
     * Es una función suspendida, así que debe ser llamada dentro de una corrutina.
     */
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = token
        }
    }

    /**
     * Recupera el token almacenado en DataStore.
     * Devuelve null si no existe ningún token guardado.
     */
    suspend fun getAuthToken(): String? {
        return context.dataStore.data
            .map { preferences -> preferences[KEY_AUTH_TOKEN] }
            .first()
    }

    /**
     * Elimina el token guardado en DataStore (ideal para cerrar sesión).
     */
    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_AUTH_TOKEN)
        }
    }
}
