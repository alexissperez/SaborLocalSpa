package com.example.saborlocalspa.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.dp


@Composable
fun HomeScreen(navController: NavHostController) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Pantalla Home")
            Button(onClick = { navController.navigate("perfil") }) { Text("Ir a Perfil") }
            Button(onClick = { navController.navigate("login") }) { Text("Ir a Login") }
            Button(onClick = { navController.navigate("register") }) { Text("Ir a Registro") }
        }
    }
}
