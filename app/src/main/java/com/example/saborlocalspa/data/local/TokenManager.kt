package com.example.saborlocalspa.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.saborlocalspa.data.remote.dto.auth.UserDto
import com.example.saborlocalspa.model.User

/**
 *
 * @param context Contexto de la aplicación (preferiblemente ApplicationContext)
 */
class TokenManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "saborlocal_encrypted_prefs"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_ROLE = "user_role"
    }

    private val encryptedPrefs: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /**
     * Obtiene el token JWT guardado
     * SINCRÓNICO - seguro para usar en interceptores
     */
    fun getToken(): String? {
        return encryptedPrefs.getString(KEY_TOKEN, null)
    }

    /**
     * Guarda el token JWT y datos del usuario
     * SINCRÓNICO - seguro para usar en interceptores
     */
    fun saveToken(token: String, user: UserDto) {
        encryptedPrefs.edit().apply {
            putString(KEY_TOKEN, token)
            putString(KEY_USER_ID, user.id)
            putString(KEY_USER_NAME, user.nombre)
            putString(KEY_USER_EMAIL, user.email)
            putString(KEY_USER_ROLE, user.role)
            apply() // Aplica cambios en background thread
        }
    }

    /**
     * Actualiza solo los datos del usuario (mantiene el token)
     */
    fun updateUserData(user: UserDto) {
        encryptedPrefs.edit().apply {
            putString(KEY_USER_ID, user.id)
            putString(KEY_USER_NAME, user.nombre)
            putString(KEY_USER_EMAIL, user.email)
            putString(KEY_USER_ROLE, user.role)
            apply()
        }
    }

    /**
     * Obtiene el usuario guardado en sesión
     * Retorna null si no hay sesión activa
     *
     * IMPORTANTE: El nombre puede ser null (algunos usuarios no tienen nombre en el backend)
     */
    fun getCurrentUser(): User? {
        val token = getToken() ?: return null
        val userId = encryptedPrefs.getString(KEY_USER_ID, null) ?: return null
        val userName = encryptedPrefs.getString(KEY_USER_NAME, null) // ✅ Permitir null
        val userEmail = encryptedPrefs.getString(KEY_USER_EMAIL, null) ?: return null
        val userRole = encryptedPrefs.getString(KEY_USER_ROLE, null) ?: return null

        return User(
            id = userId,
            nombre = userName, // ✅ Puede ser null
            email = userEmail,
            role = userRole
        )
    }

    /**
     * Verifica si hay una sesión activa
     */
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    /**
     * Limpia el token y todos los datos de sesión
     * SINCRÓNICO - seguro para usar en interceptores
     */
    fun clearToken() {
        encryptedPrefs.edit().clear().apply()
    }
}
