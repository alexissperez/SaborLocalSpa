package com.example.saborlocalspa.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.saborlocalspa.AppDependencies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ProfileUiState(
    val userName: String = "",
    val userEmail: String = "",
    val avatarUri: Uri? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val dependencies = AppDependencies.getInstance(application)
    private val userRepository = dependencies.userRepository
    private val avatarRepository = dependencies.avatarRepository

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
        loadSavedAvatar()
    }

    private fun loadUserProfile() {
        _uiState.update {
            it.copy(
                userName = "Usuario Ejemplo",
                userEmail = "ejemplo@saborlocal.cl",
                isLoading = false,
                error = null
            )
        }
    }

    private fun loadSavedAvatar() {
        viewModelScope.launch {
            avatarRepository.getAvatarUri().collect { savedUri ->
                _uiState.update { it.copy(avatarUri = savedUri) }
            }
        }
    }

    // FUNCIÓN ACTUALIZAR AVATAR -- AQUÍ DENTRO DE LA CLASE:
    fun updateAvatar(uri: Uri?) {
        viewModelScope.launch {
            avatarRepository.saveAvatarUri(uri)
        }
    }

    // Añade estas funciones ABAJO
    fun loadUser(userId: String): ProfileUiState {
        return _uiState.value
    }

    fun hasPermission(permission: String): Boolean {
        return true // o tu lógica real de permisos
    }
}
