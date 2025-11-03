package com.example.saborlocalspa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.saborlocalspa.ui.screens.HomeScreen
import com.example.saborlocalspa.ui.screens.LoginScreen
import com.example.saborlocalspa.ui.screens.RegisterScreen
import com.example.saborlocalspa.ui.screens.ProfileScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("login") { LoginScreen(onLogin = { navController.navigate("home") }) }
        composable("register") { RegisterScreen(onRegister = { navController.navigate("home") }) }
        composable("perfil") { ProfileScreen(navController) }
    }
}
