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

    // Gallery picker
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) viewModel.updateAvatar(uri)
    }

    // Camera picker
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            tempCameraUri?.let { viewModel.updateAvatar(it) }
        }
    }

    // Diálogo para elegir cámara o galería
    var showSelector by remember { mutableStateOf(false) }

    if (showSelector) {
        AlertDialog(
            onDismissRequest = { showSelector = false },
            confirmButton = { },
            dismissButton = { },
            title = { Text("Seleccionar foto de perfil") },
            text = {
                Column {
                    Button(onClick = {
                        // Cámara
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
        // Botón de retroceso
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
        // Contenido del perfil
        ProfileContent(
            uiState = state,
            puedeEditar = true,
            onRefresh = { viewModel.loadUser() },
            onEditProfile = { /* implementa navegación o dialogo de editar */ },
            onAvatarChange = { showSelector = true }
        )
    }
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
            .padding(top = 0.dp), // ya está el top padding en el Row superior
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
                                .clip(CircleShape) // <- AQUÍ
                                .background(Color(0xFF8465BE), shape = CircleShape),
                            contentScale = ContentScale.Crop // <- Y AQUÍ
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
                            .clickable { onAvatarChange(null) },
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

