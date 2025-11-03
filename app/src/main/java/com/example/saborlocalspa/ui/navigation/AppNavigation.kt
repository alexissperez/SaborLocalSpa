package com.example.saborlocalspa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.saborlocalspa.AppDependencies
import com.example.saborlocalspa.ui.screens.HomeScreen
import com.example.saborlocalspa.ui.screens.LoginScreen
import com.example.saborlocalspa.ui.screens.RegisterScreen
import com.example.saborlocalspa.ui.screens.ProfileScreen
import com.example.saborlocalspa.viewmodel.ProfileViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    appDependencies: AppDependencies
) {
    NavHost(
        navController = navController,
        startDestination = "register"
    ) {
        composable("home") {
            HomeScreen(navController, appDependencies.avatarRepository)
        }
        composable("login") {
            LoginScreen(onLogin = { navController.navigate("home") })
        }
        composable("register") {
            RegisterScreen(onRegister = { navController.navigate("home") })
        }
        composable("perfil") {
            val application = navController.context.applicationContext as android.app.Application
            val profileViewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        return ProfileViewModel(application) as T
                    }
                }
            )
            ProfileScreen(profileViewModel)
        }
    }
}

