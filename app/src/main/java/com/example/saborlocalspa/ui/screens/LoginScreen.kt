package com.example.saborlocalspa.ui.screens

// Importa componentes de Jetpack Compose y Material3
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Pantalla de inicio de sesión típica para una app Compose.
 * El parámetro onLogin se puede enlazar con un ViewModel o una función de autenticación.
 */
@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit = { _, _ -> }
) {
    // Variables de estado para los campos del formulario
    var email by remember { mutableStateOf("") }      // Correo electrónico del usuario
    var password by remember { mutableStateOf("") }   // Contraseña del usuario

    // Layout que centra el contenido en la pantalla
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Tarjeta Material para el formulario de login
        Card(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Título de la pantalla
                Text("Iniciar Sesión", style = MaterialTheme.typography.headlineSmall)

                // Campo: Correo electrónico
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Campo: Contraseña (oculta el texto)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Botón principal de login
                Button(
                    onClick = { onLogin(email, password) },   // Llama al callback de login al pulsar
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Entrar")
                }
            }
        }
    }
}

