package com.example.saborlocalspa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.saborlocalspa.ui.navigation.AppNavigation
import com.example.saborlocalspa.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val items = listOf("home", "perfil", "settings")
            var selectedItem by remember { mutableStateOf("home") }
            val navController = rememberNavController()
            val appDependencies = AppDependencies.getInstance(application)
            val profileViewModel = ProfileViewModel(appDependencies.userRepository)

            Scaffold(

            ) { innerPadding ->
                Box(Modifier.padding(innerPadding)) {
                    AppNavigation(
                        avatarRepository = appDependencies.avatarRepository,
                        profileViewModel = profileViewModel
                    )
                }
            }
        }
    }
}
