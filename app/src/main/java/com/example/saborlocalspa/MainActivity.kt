package com.example.saborlocalspa.viewmodel

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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val items = listOf("home", "perfil", "settings")
            var selectedItem by remember { mutableStateOf("home") }

            Scaffold(
                bottomBar = {
                    NavigationBar {
                        items.forEach { item ->
                            NavigationBarItem(
                                selected = selectedItem == item,
                                onClick = {
                                    selectedItem = item
                                    navController.navigate(item) {
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                label = { Text(item.replaceFirstChar { it.uppercase() }) },
                                icon = { /* Puedes poner un Icon aquí si quieres */ }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(Modifier.padding(innerPadding)) {
                    AppNavigation(navController)
                }
            }
        }
    }
}
