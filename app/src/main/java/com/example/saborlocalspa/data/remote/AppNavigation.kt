package com.example.saborlocalspa.data.remote

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saborlocalspa.repository.AvatarRepository
import com.example.saborlocalspa.viewmodel.ProfileViewModel
import com.example.saborlocalspa.ui.screens.*

@Composable
fun AppNavigation(
    avatarRepository: AvatarRepository,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("register") },
                onGuestClick = { navController.navigate("home") }
            )
        }
        composable("login") {
            LoginScreen(
                onLogin = { navController.navigate("home") }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegister = { navController.navigate("home") }
            )
        }

        composable("home") {
            HomeScreen(
                navController = navController, // Pasa navController para que HomeScreen navegue
                avatarRepository = avatarRepository
            )
        }
        composable("profile") {
            ProfileScreen(
                viewModel = profileViewModel,
                onBack = { navController.popBackStack() } // Botón volver funcional
            )
        }
    }
}
