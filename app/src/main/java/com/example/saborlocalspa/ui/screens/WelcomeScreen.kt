package com.example.saborlocalspa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import com.example.saborlocalspa.R

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGuestClick: () -> Unit
) {
    // Fondo blanco completo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            // Logo superior (puedes usar painterResource o AsyncImage)
            Image(
                painter = painterResource(R.drawable.logo), // Reemplaza por tu recurso
                contentDescription = "Logo",
                modifier = Modifier.size(180.dp)
            )
            // Título central
            Text(
                text = "Los mejores productos y con la mejor calidad",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))
            // Fila de botones (login y registro)
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onLoginClick,
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9C27B0), // Rosa GoodMeal
                        contentColor = Color.White
                    ),

                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)

                ) {
                    Text("Iniciar sesión", style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(Modifier.width(16.dp))
                OutlinedButton(
                    onClick = onRegisterClick,
                    shape = RoundedCornerShape(32.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Text("Crear cuenta", style = MaterialTheme.typography.bodyLarge)
                }
            }
            // Botón de invitado al pie
            TextButton(
                onClick = onGuestClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Ingresar como invitado",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = Color.Black
                )
            }
        }
    }
}
