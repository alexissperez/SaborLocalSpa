// LoginScreen.kt
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

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var messageColor by remember { mutableStateOf(Color(0xFF8B5CF6)) }
    val isEmailValid = email.endsWith("@gmail.com") && email.length > 10
    val isPasswordValid = password.length <= 8

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Iniciar Sesión",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF7C3AED)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("ejemplo@gmail.com") },
                    isError = email.isNotBlank() && !isEmailValid,
                    singleLine = true,
                    supportingText = {
                        if (email.isNotBlank() && !isEmailValid)
                            Text("El correo debe terminar en @gmail.com")
                    }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        if (it.length <= 8) password = it
                    },
                    label = { Text("Contraseña") },
                    placeholder = { Text("8 caracteres máx.") },
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
                    }
                )
                Button(
                    onClick = {
                        when {
                            email.isBlank() || password.isBlank() -> {
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
                                message = "Inicio de sesión exitoso"
                                messageColor = Color(0xFF8B5CF6)
                                onLogin()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Entrar")
                }
                if (message.isNotEmpty()) {
                    Text(message, color = messageColor)
                }
            }
        }
    }
}
