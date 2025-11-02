package com.example.saborlocalspa.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.saborlocalspa.repository.UserRepository
import com.example.saborlocalspa.data.remote.dto.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estado de la UI para el perfil de usuario.
 */
data class ProfileUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val userEmail: String = "",
    val error: String? = null
)

/**
 * ViewModel: Maneja la lógica de UI y el estado de perfil de usuario.
 */
class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    // Estado privado para actualización interna
    private val _uiState = MutableStateFlow(ProfileUiState())

    // Estado público para que la UI lo observe
    val uiState: StateFlow<ProfileUiState> = _uiState

    /**
     * Carga los datos del usuario desde la API usando el repositorio.
     */
    fun loadUser(id: Int = 1) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            val result = repository.fetchUser(id)
            result.fold(
                onSuccess = { user ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        userName = user.username, // Cambia por el campo correcto si tu DTO usa 'name'
                        userEmail = user.email ?: "Sin email",
                        error = null
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.localizedMessage ?: "Error desconocido"
                    )
                }
            )
        }
    }
}
