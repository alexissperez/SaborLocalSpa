package com.example.saborlocalspa.ui.screens

// Importa los componentes básicos de Compose UI y Material
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Pantalla para registrar un nuevo usuario.
 * @param onRegister función callback cuando el usuario pulsa "Registrarme"
 */
@Composable
fun RegisterScreen(
    onRegister: (String, String, String) -> Unit = { _, _, _ -> }
) {
    // Variables UI para los campos del formulario
    var name by remember { mutableStateOf("") }      // Nombre completo
    var email by remember { mutableStateOf("") }     // Correo electrónico
    var password by remember { mutableStateOf("") }  // Contraseña

    // Centra el contenido en la pantalla
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Tarjeta de registro
        Card(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Título
                Text("Registrar Usuario", style = MaterialTheme.typography.headlineSmall)

                // Campo: Nombre completo
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre completo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Campo: Correo electrónico
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Campo: Contraseña (enmascarada)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Botón de registro
                Button(
                    onClick = { onRegister(name, email, password) }, // Llama el callback definido por la UI
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarme")
                }
            }
        }
    }
}
