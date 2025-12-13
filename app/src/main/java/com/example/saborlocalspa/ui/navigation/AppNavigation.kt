package com.example.saborlocalspa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saborlocalspa.ui.screens.*


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        /**
         * Ruta: login
         *
         * Pantalla inicial de la app. Permite:
         * - Iniciar sesión con email/password
         * - Navegar a registro si no tiene cuenta
         * - Login exitoso → Navega a home limpiando backstack
         */
        composable("login") {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        /**
         * Ruta: register
         *
         * Pantalla de registro de nuevos usuarios. Permite:
         * - Crear cuenta con name, email, password
         * - Volver a login con navigateUp()
         * - Registro exitoso → Navega a home limpiando backstack
         */
        composable("register") {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigateUp()
                },
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        /**
         * Ruta: home
         *
         * Pantalla principal con Bottom Navigation Bar.
         * Contiene 5 tabs: Inicio, Productos, Carrito, Pedidos, Perfil.
         *
         * Solo accesible tras autenticación exitosa.
         */
        composable("home") {
            MainScreen(
                onLogout = {
                    // Navegar al login y limpiar todo el backstack
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
