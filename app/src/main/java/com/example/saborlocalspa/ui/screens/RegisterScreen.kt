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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.remember
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import com.example.saborlocalspa.viewmodel.ProfileViewModel


@Composable
fun RegisterScreen(
    profileViewModel: ProfileViewModel,
    onBack: () -> Unit = {},
    onShowTerms: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var acceptTerms by remember { mutableStateOf(false) }

    val isEmailValid = email.endsWith("@gmail.com") && email.length > 10
    val isPasswordValid = password.length <= 8
    val isReady = name.isNotBlank() && isEmailValid && isPasswordValid && acceptTerms

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
            }
        }
        Spacer(Modifier.height(10.dp))
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally),
            tint = Color(0xFFD946EF).copy(alpha = 0.6f)
        )
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            leadingIcon = { Icon(Icons.Filled.Person, null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Filled.Email, null) },
            placeholder = { Text("ejemplo@gmail.com") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = email.isNotBlank() && !isEmailValid,
            supportingText = {
                if (email.isNotBlank() && !isEmailValid)
                    Text("El correo debe terminar en @gmail.com")
            }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { if (it.length <= 8) password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Filled.Lock, null) },
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
                if (password.length > 8)
                    Text("Máximo 8 caracteres", color = Color.Red)
            }
        )
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = acceptTerms,
                onCheckedChange = { acceptTerms = it },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFD946EF))
            )
            TextButton(onClick = onShowTerms) {
                Text("Acepto los Términos y Condiciones", color = Color(0xFFD946EF))
            }
        }
        Spacer(Modifier.height(24.dp))
        // Botón modificado para actualizar datos y navegar
        Button(
            onClick = {
                profileViewModel.updateUserName(name)
                profileViewModel.updateUserEmail(email)
                onRegisterSuccess()
            },
            enabled = isReady,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isReady) Color(0xFFD946EF) else Color.LightGray
            )
        ) {
            Text("Crear cuenta", color = if (isReady) Color.White else Color.Gray)
        }
    }
}
