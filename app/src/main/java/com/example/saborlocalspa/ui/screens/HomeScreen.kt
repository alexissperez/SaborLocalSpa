package com.example.saborlocalspa.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    // ... otros contenidos
    Button(onClick = { navController.navigate("perfil") }) {
        Text("Ver Perfil")
    }
}
