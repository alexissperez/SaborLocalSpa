package com.example.saborlocalspa.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val userEmail: String = "",
    val error: String? = null,
    val formattedCreatedAt: String = "",
    val avatarUri: Uri? = null
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    // Versión temporal: muestra datos de ejemplo siempre
    fun loadUser() {
        val nombres = listOf("Ana", "Beto", "Carlos", "Dani")
        val aleatorio = nombres.random()
        _uiState.value = _uiState.value.copy(
            userName = aleatorio,
            userEmail = "$aleatorio@email.com"
        )
    }


    fun updateAvatar(uri: Uri?) {
        _uiState.update { it.copy(avatarUri = uri) }
    }
}
