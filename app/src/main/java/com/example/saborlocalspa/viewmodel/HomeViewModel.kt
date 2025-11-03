package com.example.saborlocalspa.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel para la pantalla de Home (principal),
 * responsable de manejar el estado, lógica y comunicación con repositorios si lo necesitas.
 */
class HomeViewModel : ViewModel() {

    // Un estado observable para la UI. Por ejemplo, un mensaje de bienvenida.
    private val _welcomeText = MutableStateFlow("¡Bienvenido a Sabor Local!")
    val welcomeText: StateFlow<String> = _welcomeText

    // Puedes agregar aquí otros estados y lógicas:
    // - Estados para lista de productos, banners, etc.
    // - Funciones de carga de datos desde repositorio/remoto/local.
    // - Funciones para manipular el estado (agregar item al carrito, refrescar productos, etc).

    // Ejemplo: función para cambiar el mensaje de bienvenida (puedes conectar esto a un botón o evento de UI)
    fun updateWelcomeText(newText: String) {
        _welcomeText.value = newText
    }
}
