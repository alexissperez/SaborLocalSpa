// RegisterScreen.kt
package com.example.saborlocalspa.ui.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(onRegister: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var messageColor by remember { mutableStateOf(Color(0xFF8B5CF6)) } // Morado

    // Estados para mostrar datos registrados tras éxito
    var registeredName by remember { mutableStateOf("") }
    var registeredEmail by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Registrar Usuario",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF7C3AED)
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre completo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Button(
                    onClick = {
                        if (name.isBlank() || email.isBlank() || password.isBlank()) {
                            message = "Completa todos los campos"
                            messageColor = Color(0xFFD946EF)
                        } else {
                            registeredName = name
                            registeredEmail = email
                            message = "Registro exitoso"
                            messageColor = Color(0xFF8B5CF6)
                            onRegister()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarme")
                }
                if (message.isNotEmpty()) {
                    Text(message, color = messageColor)
                }

                // Muestra el nombre y email registrados si existen (opcional)
                if (registeredName.isNotBlank() && registeredEmail.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nombre registrado: $registeredName")
                    Text("Email registrado: $registeredEmail")
                }
            }
        }
    }
}
