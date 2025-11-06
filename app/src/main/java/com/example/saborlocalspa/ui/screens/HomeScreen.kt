package com.example.saborlocalspa.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.saborlocalspa.R
import androidx.navigation.NavController
import com.example.saborlocalspa.repository.AvatarRepository

@Composable
fun HomeScreen(
    navController: NavController,
    avatarRepository: AvatarRepository
)  {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(horizontal = 16.dp) // Uniforme a los lados
    ) {

        // Buscador
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("¿Qué quieres comer?") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        // Marcas Populares
        Text("Marcas más populares", Modifier.padding(start = 16.dp, top = 4.dp), color = Color.DarkGray)
        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(painterResource(id = R.drawable.logomasa), null, Modifier.size(48.dp))
            Image(painterResource(id = R.drawable.logoartesanal), null, Modifier.size(48.dp))
        }

        // Ofertas/Carrusel
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF9E5F9)
            ),
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painterResource(id = R.drawable.frambuesa), null, Modifier.size(80.dp))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Frutos del Valle", style = MaterialTheme.typography.titleMedium)
                    Text("Frambuesa Artesanal", color = Color.Gray)
                }
            }
        }

        // Productos mas vendidos
        Text("Productos mas vendidos", Modifier.padding(start = 16.dp, top = 4.dp), color = Color.DarkGray)
        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(painterResource(id = R.drawable.panmasamadre), null, Modifier.size(48.dp))
            Image(painterResource(id = R.drawable.quesoa), null, Modifier.size(48.dp))
            Image(painterResource(id = R.drawable.frambuesa), null, Modifier.size(48.dp))
            Image(painterResource(id = R.drawable.picklestarro), null, Modifier.size(48.dp))
            Image(painterResource(id = R.drawable.mantequillacampo), null, Modifier.size(48.dp))
        }

        // Novedades / Cards de productos
        Text("Lo más nuevo", Modifier.padding(start = 16.dp, top = 8.dp), color = Color.Gray)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
                Column(Modifier.padding(16.dp)) {
                    Image(painterResource(id = R.drawable.pickles), null, Modifier.height(130.dp).fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    Text("Pickles", style = MaterialTheme.typography.titleMedium)
                    Text("Frasco pickles encurtidos $4990 ", color = Color.Gray)
                    Button(
                        onClick = { /* ver más... */ },
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD946EF))
                    ) { Text("Ver más", color = Color.White) }
            }
        }

        Spacer(Modifier.height(80.dp))
        }
    }

