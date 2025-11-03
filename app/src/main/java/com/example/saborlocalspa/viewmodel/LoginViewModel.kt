package com.example.saborlocalspa.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel para la pantalla de inicio de sesión (Login).
 * Aquí gestionas el estado del formulario, llamadas de autenticación y errores.
 */
class LoginViewModel : ViewModel() {

    // Estado de autenticación (puede ser usado para mostrar loading, errores, etc.)
    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState: StateFlow<LoginUiState> = _loginState

    /**
     * Lógica para intentar iniciar sesión.
     * Puedes conectar esta función a tu repositorio de usuarios o autenticación.
     */
    fun login(email: String, password: String) {
        // Aquí va la lógica real de autenticación
        // Este ejemplo es totalmente local/dummy:
        if (email == "demo@correo.com" && password == "1234") {
            _loginState.value = LoginUiState.Success
        } else {
            _loginState.value = LoginUiState.Error("Credenciales incorrectas")
        }
    }
}

/**
 * UI State representando los distintos estados posibles en la pantalla login.
 */
sealed class LoginUiState {
    object Idle : LoginUiState()                  // Estado inicial
    object Success : LoginUiState()               // Login correcto
    data class Error(val message: String) : LoginUiState() // Login fallido/error
}
