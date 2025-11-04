package com.example.saborlocalspa.ui.screens

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import androidx.core.content.FileProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.shape.CircleShape
import com.example.saborlocalspa.viewmodel.ProfileUiState
import com.example.saborlocalspa.viewmodel.ProfileViewModel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale



@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit = {}
) {
    LaunchedEffect(Unit) { viewModel.loadUser() }
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) viewModel.updateAvatar(uri)
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            tempCameraUri?.let { viewModel.updateAvatar(it) }
        }
    }

    var showSelector by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    if (showSelector) {
        // ...Tu código AlertDialog para elegir cámara/galería...
        AlertDialog(
            onDismissRequest = { showSelector = false },
            confirmButton = { },
            dismissButton = { },
            title = { Text("Seleccionar foto de perfil") },
            text = {
                Column {
                    Button(onClick = {
                        val photoFile = File.createTempFile("avatar_", ".jpg", context.cacheDir)
                        val uri = FileProvider.getUriForFile(
                            context,
                            context.packageName + ".provider",
                            photoFile
                        )
                        tempCameraUri = uri
                        cameraLauncher.launch(uri)
                        showSelector = false
                    }) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Tomar foto")
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        galleryLauncher.launch("image/*")
                        showSelector = false
                    }) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Elegir de galería")
                    }
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver"
                )
            }
        }
        ProfileContent(
            uiState = state,
            puedeEditar = true,
            onRefresh = { viewModel.loadUser() },
            onEditProfile = { showEditDialog = true },
            onAvatarChange = { showSelector = true }
        )
    }

    if (showEditDialog) {
        EditProfileDialog(
            inicialNombre = state.userName,
            onGuardar = { nuevoNombre ->
                // Actualiza nombre en ViewModel
                viewModel.updateUserName(nuevoNombre)
                showEditDialog = false
            },
            onCancelar = { showEditDialog = false }
        )
    }
}


@Composable
fun EditProfileDialog(
    inicialNombre: String,
    onGuardar: (String) -> Unit,
    onCancelar: () -> Unit
) {
    var nombre by remember { mutableStateOf(inicialNombre) }
    var error by remember { mutableStateOf<String?>(null) }
    AlertDialog(
        onDismissRequest = onCancelar,
        confirmButton = {
            Button(onClick = {
                if (nombre.isBlank()) error = "El nombre es obligatorio"
                else onGuardar(nombre)
            }) { Text("Guardar") }
        },
        dismissButton = { Button(onClick = onCancelar) { Text("Cancelar") } },
        title = { Text("Editar Nombre") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        error = if (it.isBlank()) "El nombre es obligatorio" else null
                    },
                    isError = error != null,
                    label = { Text("Nombre") }
                )
                if (error != null) {
                    Text(error!!, color = Color.Red)
                }
            }
        }
    )
}

