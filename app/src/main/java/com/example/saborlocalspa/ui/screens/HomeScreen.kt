package com.example.saborlocalspa.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * Pantalla principal (Home) de la app, recibe un NavHostController
 * para poder navegar a otras pantallas.
 */
@Composable
fun HomeScreen(navController: NavHostController) {
    // Puedes agregar aquí más contenido (Text, imágenes, etc)

    // Botón para navegar a la pantalla de perfil.
    // Al hacer click, el controlador de navegación cambia a la ruta "perfil".
    Button(onClick = { navController.navigate("perfil") }) {
        Text("Ver Perfil") // El texto mostrado en el botón
    }
}
