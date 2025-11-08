package com.example.saborlocalspa.repository

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property para obtener tu almacén de preferencias
private val Context.avatarDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "avatar_preferences"
)

class AvatarRepository(private val context: Context) {
    companion object {
        private val AVATAR_URI_KEY = stringPreferencesKey("avatar_uri_key")
    }

    // Observa cambios en la URI del avatar como Flow
    fun getAvatarUri(): Flow<Uri?> {
        return context.avatarDataStore.data.map { preferences ->
            preferences[AVATAR_URI_KEY]?.let { uriString ->
                Uri.parse(uriString)
            }
        }
    }

    // Guarda la URI del avatar en DataStore
    suspend fun saveAvatarUri(uri: Uri?) {
        context.avatarDataStore.edit { preferences ->
            if (uri != null) {
                preferences[AVATAR_URI_KEY] = uri.toString()
            } else {
                preferences.remove(AVATAR_URI_KEY)
            }
        }
    }

    // Borra la URI guardada
    suspend fun clearAvatarUri() {
        context.avatarDataStore.edit { preferences ->
            preferences.remove(AVATAR_URI_KEY)
        }
    }
}


