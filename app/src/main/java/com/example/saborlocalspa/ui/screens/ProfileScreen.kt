package com.example.saborlocalspa.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.saborlocalspa.viewmodel.ProfileViewModel
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val state by viewModel.uiState.collectAsState()

    // Aquí va tu UI
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nombre: ${state.userName}")
        Text("Email: ${state.userEmail}")
        // Puedes agregar más componentes aquí:
        // - imagen de avatar si state.avatarUri != null
        // - botón para refrescar
        // - otras acciones
    }
}
