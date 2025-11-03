package com.example.saborlocalspa.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel para la pantalla de registro.
 * Maneja el estado del proceso de registro, posibles errores y éxito.
 */
class RegisterViewModel : ViewModel() {

    // Estado observable para el formulario de registro (éxito, error, idle, loading)
    private val _registerState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val registerState: StateFlow<RegisterUiState> = _registerState

    /**
     * Lógica de registro de usuario.
     * Simulado: si email o password están vacíos, da error; si no, éxito.
     * Aquí puedes conectar un repositorio real o una API.
     */
    fun register(name: String, email: String, password: String) {
        if (email.isBlank() || password.isBlank() || name.isBlank()) {
            _registerState.value = RegisterUiState.Error("Todos los campos son obligatorios")
        } else if (!email.contains("@")) {
            _registerState.value = RegisterUiState.Error("El correo electrónico no es válido")
        } else {
            // Aquí iría la llamada a tu repositorio/API remoto/local
            _registerState.value = RegisterUiState.Success
        }
    }
}

/**
 * Estados de la UI para el registro.
 */
sealed class RegisterUiState {
    object Idle : RegisterUiState()                   // Estado inicial o listo
    object Success : RegisterUiState()                // Registro exitoso
    data class Error(val message: String) : RegisterUiState() // Registro falló con mensaje
}
