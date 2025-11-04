// RegisterScreen.kt
package com.example.saborlocalspa.ui.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

object SesionUsuario {
    var nombre: String? = null
    var email: String? = null
}

@Composable
fun RegisterScreen(onRegister: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var messageColor by remember { mutableStateOf(Color(0xFF8B5CF6)) } // Morado

    var registeredName by remember { mutableStateOf("") }
    var registeredEmail by remember { mutableStateOf("") }

    val isEmailValid = email.endsWith("@gmail.com") && email.length > 10
    val isPasswordValid = password.length <= 8

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
                    singleLine = true,
                    placeholder = { Text("ejemplo@gmail.com") },
                    isError = email.isNotBlank() && !isEmailValid,
                    supportingText = {
                        if (email.isNotBlank() && !isEmailValid) {
                            Text("El correo debe terminar en @gmail.com", color = Color.Red)
                        }
                    }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        if (it.length <= 8) password = it
                    },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    supportingText = {
                        if (password.length > 8) {
                            Text("Máximo 8 caracteres", color = Color.Red)
                        }
                    }
                )
                Button(
                    onClick = {
                        when {
                            name.isBlank() || email.isBlank() || password.isBlank() -> {
                                message = "Completa todos los campos"
                                messageColor = Color(0xFFD946EF)
                            }
                            !isEmailValid -> {
                                message = "El correo debe terminar en @gmail.com"
                                messageColor = Color(0xFFD946EF)
                            }
                            !isPasswordValid -> {
                                message = "La contraseña debe tener máximo 8 caracteres"
                                messageColor = Color(0xFFD946EF)
                            }
                            else -> {
                                registeredName = name
                                registeredEmail = email
                                message = "Registro exitoso"
                                messageColor = Color(0xFF8B5CF6)
                                onRegister()
                            }
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

                if (registeredName.isNotBlank() && registeredEmail.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nombre registrado: $registeredName")
                    Text("Email registrado: $registeredEmail")
                }
            }
        }
    }
}
