package com.example.saborlocalspa.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.saborlocalspa.viewmodel.ProfileViewModel
import android.net.Uri
import com.example.saborlocalspa.viewmodel.ProfileUiState

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    LaunchedEffect(Unit) { viewModel.loadUser() }
    val state by viewModel.uiState.collectAsState()

    ProfileContent(
        uiState = state,
        puedeEditar = true, // O tu lógica según usuario
        onRefresh = { viewModel.loadUser() },
        onEditProfile = { /* lógica para editar */ },
        onAvatarChange = { uri -> viewModel.updateAvatar(uri) }
    )
}

@Composable
fun ProfileContent(
    uiState: ProfileUiState,
    puedeEditar: Boolean,
    onRefresh: () -> Unit,
    onEditProfile: () -> Unit = {},
    onAvatarChange: (Uri?) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFD1B2FF))
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .heightIn(min = 340.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF6FF))
        ) {
            Column(
                Modifier.padding(32.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- Avatar ---
                Box(
                    Modifier.size(116.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    if (uiState.avatarUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(uiState.avatarUri),
                            contentDescription = "Avatar del usuario",
                            modifier = Modifier
                                .size(116.dp)
                                .background(Color(0xFF8465BE), shape = CircleShape),
                        )
                    } else {
                        Surface(
                            modifier = Modifier
                                .size(116.dp)
                                .background(Color(0xFF8465BE), CircleShape),
                            shape = CircleShape,
                            color = Color(0xFF8465BE)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Avatar default",
                                tint = Color(0xFFF3EAFE),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(30.dp)
                            )
                        }
                    }
                    Surface(
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { /* implementar picker */ },
                        shape = CircleShape,
                        color = Color(0xFFF1DEFF)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CameraAlt,
                            contentDescription = "Cambiar foto",
                            tint = Color(0xFF6946A1),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                Spacer(Modifier.height(18.dp))
                Text(
                    text = uiState.userName,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF6946A1)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = uiState.userEmail,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF6946A1)
                )
                Spacer(Modifier.height(18.dp))
                if (puedeEditar) {
                    Button(
                        onClick = onEditProfile,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBB86FC))
                    ) {
                        Text("Editar Perfil", color = Color.White)
                    }
                } else {
                    Text(
                        text = "No tienes permisos para editar el perfil.",
                        color = Color.Red
                    )
                }
            }
        }
        Spacer(Modifier.height(30.dp))
        Button(
            onClick = onRefresh,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8465BE))
        ) {
            Text("Refrescar", color = Color.White)
        }
    }
}
