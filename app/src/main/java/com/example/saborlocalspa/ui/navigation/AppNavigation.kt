package com.example.saborlocalspa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saborlocalspa.repository.AvatarRepository
import com.example.saborlocalspa.viewmodel.ProfileViewModel
import com.example.saborlocalspa.ui.screens.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.padding

@Composable
fun AppNavigation(
    avatarRepository: AvatarRepository,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()
    val currentRoute = getCurrentRoute(navController)

    // Lista de pantallas donde NO se debe mostrar la barra inferior
    val hideBottomBarRoutes = listOf("welcome", "login", "register", "forgotPassword")
    val showBottomBar = currentRoute !in hideBottomBarRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "home",
                        onClick = { navController.navigate("home") },
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text("Inicio") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "tiendas",
                        onClick = { navController.navigate("tiendas") },
                        icon = { Icon(Icons.Filled.Store, contentDescription = null) },
                        label = { Text("Tiendas") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "profile",
                        onClick = { navController.navigate("profile") },
                        icon = { Icon(Icons.Filled.Person, contentDescription = null, tint = Color(0xFFD946EF)) },
                        label = { Text("Perfil", color = Color(0xFFD946EF)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("welcome") {
                WelcomeScreen(
                    onLoginClick = { navController.navigate("login") },
                    onRegisterClick = { navController.navigate("register") },
                    onGuestClick = { navController.navigate("home") }
                )
            }
            composable("login") {
                LoginScreen(
                    onLogin = { navController.navigate("home") },
                    onBack = { navController.popBackStack() },
                    onForgotPassword = { navController.navigate("forgotPassword") }
                )
            }
            composable("forgotPassword") {
                ForgotPasswordScreen(
                    onBack = { navController.popBackStack() },
                    onLogin = { navController.navigate("login") },
                    onSendRecovery = { email -> }
                )
            }
            composable("register") {
                RegisterScreen(
                    profileViewModel = profileViewModel,
                    onBack = { navController.popBackStack() }, // <-- Esto permite volver atrás
                    onRegisterSuccess = {
                        navController.navigate("profile") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                )
            }
            composable("profile") {
                ProfileScreen(
                    viewModel = profileViewModel,
                    onLogout = {
                        navController.navigate("welcome") {
                            popUpTo("welcome") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable("home") {
                HomeScreen(
                    navController = navController,
                    avatarRepository = avatarRepository
                )
            }

        }
    }
}


@Composable
fun getCurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route}


