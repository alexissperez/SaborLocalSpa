package com.example.saborlocalspa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.saborlocalspa.ui.screens.ProfileScreen

/**
 * Define la navegación de la app usando NavHost.
 *
 * @param navController El NavHostController que gestiona el stack de navegación.
 */
@Composable
fun AppNavigation(navController: NavHostController) {
    // NavHost define el grafo de navegación.
    // startDestination indica la pantalla donde inicia la app ("perfil" en este caso).
    NavHost(
        navController = navController,
        startDestination = "perfil"
    ) {
        // Cada "composable" aquí es una ruta/pantalla.
        // Para navegar, usa navController.navigate("ruta")
        composable("perfil") {
            ProfileScreen() // Muestra la pantalla de perfil al entrar a la ruta "perfil"
        }
    }
}
