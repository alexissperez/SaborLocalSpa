package com.example.saborlocalspa.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.saborlocalspa.viewmodel.ProfileViewModel
import com.example.saborlocalspa.viewmodel.ProfileUiState
import com.example.saborlocalspa.ui.components.ImagePickerDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch
import com.google.accompanist.permissions.*


// --- Paleta morada personalizada ---
val Purple80 = Color(0xFFD0BCFF)
val Purple40 = Color(0xFF6650a4)
val PurpleDark = Color(0xFF512da8)
val ButtonTextColor = Color.White

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple80)
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "❌ Error",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.error ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = PurpleDark),
                        onClick = { viewModel.loadUser("1234") }
                    ) {
                        Text("Reintentar", color = ButtonTextColor)
                    }
                }
            }
            else -> {
                ProfileContent(
                    uiState = state,
                    puedeEditar = viewModel.hasPermission("editar_perfil"),
                    onRefresh = {
                        viewModel.loadUser("1234")
                        scope.launch {
                            snackbarHostState.showSnackbar("Perfil recargado")
                        }
                    },
                    onEditProfile = {
                        // Aquí puedes navegar a una pantalla o abrir un diálogo. Usaremos un snackbar de ejemplo.
                        scope.launch { snackbarHostState.showSnackbar("Editar perfil (pendiente)") }
                    },
                    onAvatarChange = { uri -> viewModel.updateAvatar(uri) }
                )
                SnackbarHost(snackbarHostState, Modifier.align(Alignment.BottomCenter))
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    puedeEditar: Boolean,
    onRefresh: () -> Unit,
    onEditProfile: () -> Unit = {},
    onAvatarChange: (Uri?) -> Unit
) {
    val context = LocalContext.current
    var showImagePicker by remember { mutableStateOf(false) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // ... (permiso y pickers igual que antes)

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFD1B2FF)) // Fondo morado claro
            .padding(top = 40.dp), // Baja el Card un poco
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .heightIn(min = 340.dp),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF6FF))
        ) {
            Column(
                Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier.size(116.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    if (uiState.avatarUri != null) {
                        AsyncImage(
                            model = uiState.avatarUri,
                            contentDescription = "Avatar del usuario",
                            modifier = Modifier
                                .size(116.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF8465BE)) // Morado diferente para el avatar
                                .clickable { showImagePicker = true },
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    } else {
                        Surface(
                            modifier = Modifier
                                .size(116.dp)
                                .clip(CircleShape)
                                .clickable { showImagePicker = true },
                            color = Color(0xFF8465BE)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Seleccionar avatar",
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
                            .clickable { showImagePicker = true },
                        shape = CircleShape,
                        color = Color(0xFFF1DEFF),
                        shadowElevation = 4.dp
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

private fun createImageUri(context: Context): Uri? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "profile_avatar_$timeStamp.jpg"
    val storageDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
    return try {
        val imageFile = File(storageDir, imageFileName)
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    } catch (e: Exception) {
        null
    }
}