package com.example.saborlocalspa.ui.screens

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp

object SesionUsuario {
    var nombre: String? = null
    var email: String? = null
}
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit // <-- Navega al login/bienvenida aquí
) {
    LaunchedEffect(Unit) { viewModel.loadUser() }
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Launchers para foto/galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) viewModel.updateAvatar(uri)
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) { tempCameraUri?.let { viewModel.updateAvatar(it) } }
    }
    var showSelector by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    if (showSelector) {
        AlertDialog(
            onDismissRequest = { showSelector = false },
            confirmButton = {},
            dismissButton = {},
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

    if (showEditDialog) {
        EditProfileDialog(
            inicialNombre = SesionUsuario.nombre ?: state.userName,
            onGuardar = { nuevoNombre ->
                SesionUsuario.nombre = nuevoNombre
                viewModel.updateUserName(nuevoNombre)
                showEditDialog = false
            },
            onCancelar = { showEditDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F4FA))
            .verticalScroll(rememberScrollState())
    ) {
        // --------- AVATAR EDITABLE ----------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, bottom = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(94.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEDE9F7))
                    .clickable { showSelector = true } // Abre selector
            ) {
                if (!state.avatarUrl.isNullOrBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter(state.avatarUrl),
                        contentDescription = "Avatar",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar",
                        modifier = Modifier.fillMaxSize().padding(10.dp),
                        tint = Color(0xFFD946EF)
                    )
                }
            }
        }

        Spacer(Modifier.height(6.dp))
        // --------- NOMBRE Y CORREO ---------
        Text(
            SesionUsuario.nombre ?: state.userName,
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF222222),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            SesionUsuario.email ?: state.userEmail,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )



        Spacer(Modifier.height(20.dp))
        // --------- MÉTRICAS / AVANCES ---------

        Spacer(Modifier.height(18.dp))
        // --------- MENÚ ---------
        PerfilMenuEntry(icon = Icons.Filled.Person, text = "Mi cuenta") { /* navegar */ }
        PerfilMenuEntry(icon = Icons.Filled.Edit, text = "Editar perfil") { showEditDialog = true }
        PerfilMenuEntry(icon = Icons.Filled.ExitToApp, text = "Cerrar sesión", color = Color.Red) { onLogout() }

        Spacer(Modifier.height(22.dp))
    }
}
@Composable
fun PerfilMetricBox(title: String, subtitle: String, icon: ImageVector) {
    Column(
        Modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(12.dp)
            .width(92.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, null, tint = Color(0xFFD946EF), modifier = Modifier.size(32.dp))
        Spacer(Modifier.height(4.dp))
        Text(title, style = MaterialTheme.typography.titleMedium)
        Text(subtitle, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun PerfilMenuEntry(icon: ImageVector, text: String, color: Color = Color.Black, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = color)
        Spacer(Modifier.width(18.dp))
        Text(text, color = color, fontSize = 17.sp)
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
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onCancelar) {
                Text("Cancelar")
            }
        },
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


@Composable
fun ProfileContent(
    uiState: ProfileUiState,
    puedeEditar: Boolean,
    onRefresh: () -> Unit,
    onEditProfile: () -> Unit,
    onAvatarChange: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable(enabled = puedeEditar, onClick = onAvatarChange)
                .align(Alignment.CenterHorizontally)
        ) {
            if (!uiState.avatarUrl.isNullOrBlank()) {
                Image(
                    painter = rememberAsyncImagePainter(uiState.avatarUrl),
                    contentDescription = "Avatar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Usuario: ${uiState.userName}", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        if (puedeEditar) {
            Button(onClick = onEditProfile, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Editar Perfil")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRefresh, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Refrescar datos")
        }
    }
}

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    onSendRecovery: (String) -> Unit = {},
    onLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    val isEmailValid = email.endsWith("@gmail.com") && email.length > 10

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
            }
        }

        Spacer(Modifier.height(8.dp))
        // Logo circular
        Surface(
            color = Color(0xFFD946EF),
            shape = RoundedCornerShape(100),
            modifier = Modifier.size(90.dp),
            shadowElevation = 6.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Filled.Face, //
                    contentDescription = "Avatar",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        Spacer(Modifier.height(18.dp))
        Text("Recuperar contraseña", style = MaterialTheme.typography.headlineMedium, color = Color.Black)

        Text("Correo", style = MaterialTheme.typography.bodyLarge, color = Color.Black)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("correo@gmail.com") },
            leadingIcon = { Icon(Icons.Filled.Email, null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = email.isNotBlank() && !isEmailValid,
            supportingText = {
                if (email.isNotBlank() && !isEmailValid)
                    Text("El correo debe terminar en @gmail.com")
            }
        )

        Spacer(Modifier.height(32.dp))
        Button(
            onClick = { onSendRecovery(email) },
            enabled = isEmailValid,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD946EF)),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(32.dp))
        ) {
            Text("Enviar correo de recuperación", color = Color.White)
        }
        Spacer(Modifier.height(18.dp))
        Row {
            TextButton(onClick = onLogin) {
                Text("Volver al Login", color = Color(0xFFD946EF))
            }
        }
    }
}
