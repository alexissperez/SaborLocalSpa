package com.example.saborlocalspa.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// Agrega aquí estas líneas, ANTES de @Composable
var registeredName = ""
var registeredEmail = ""

@Composable
fun ProfileScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Perfil de usuario", style = MaterialTheme.typography.headlineSmall, color=Color(0xFF7C3AED))
                Text("Nombre: $registeredName")
                Text("Email: $registeredEmail")
                Spacer(Modifier.height(16.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                    onClick = { navController.navigate("home") }
                ) { Text("Ir a Home") }
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                    onClick = { navController.navigate("login") }
                ) { Text("Cerrar sesión") }
            }
        }
    }
}
