package com.example.saborlocalspa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.saborlocalspa.model.Producto
import com.example.saborlocalspa.ui.components.*
import com.example.saborlocalspa.viewmodel.HomeUiState
import com.example.saborlocalspa.viewmodel.HomeViewModel
import com.example.saborlocalspa.viewmodel.ProductoViewModel
import androidx.compose.ui.platform.LocalContext
import com.example.saborlocalspa.data.local.CarritoManager
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToProfile: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    onNavigateToCamera: () -> Unit = {},
    onNavigateToProductosList: () -> Unit = {},
    onNavigateToCreateProducto: () -> Unit = {},
    onNavigateToCreateProductor: () -> Unit = {},
    onNavigateToProductoresList: () -> Unit = {},
    onNavigateToCarrito: () -> Unit = {},
    onNavigateToPedidos: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    val recentProductsState by viewModel.recentProducts.collectAsState()
    val featuredProductoresState by viewModel.featuredProductores.collectAsState()
    val currentRole by viewModel.currentRole.collectAsState()

    var selectedProduct by remember { mutableStateOf<Producto?>(null) }
    val context = LocalContext.current

    val productoViewModel: ProductoViewModel = viewModel()

    // Bottom sheet de detalle de producto
    if (selectedProduct != null) {
        val esProductor =
            currentRole?.equals("PRODUCTOR", ignoreCase = true) == true ||
                    currentRole?.equals("Productor", ignoreCase = true) == true ||
                    currentRole?.equals("ADMIN", ignoreCase = true) == true

        ProductDetailSheet(
            producto = selectedProduct!!,
            esProductor = esProductor,
            onDismissRequest = { selectedProduct = null },
            onAddToCart = { producto ->
                CarritoManager.addItem(producto)
                Toast.makeText(
                    context,
                    "Producto agregado al carrito",
                    Toast.LENGTH_SHORT
                ).show()
                selectedProduct = null
            },
            onDelete = { producto ->
                productoViewModel.deleteProducto(producto.id)
                Toast.makeText(
                    context,
                    "Producto eliminado",
                    Toast.LENGTH_SHORT
                ).show()
                selectedProduct = null
            }
        )
    }


    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Agriculture,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SaborLocal",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(onClick = onNavigateToProfile) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Perfil",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Buscar productos, productores...") },
                        leadingIcon = {
                            Icon(Icons.Filled.Search, contentDescription = null)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Filled.Clear, contentDescription = "Limpiar")
                                }
                            }
                        },
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // HEADER
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Hola! 👋",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "¿Qué quieres comprar hoy?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    currentRole?.let { role ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rol actual: $role",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // ACCIONES RÁPIDAS COMUNES
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        QuickActionChip(
                            icon = Icons.Filled.Group,
                            label = "Productores",
                            onClick = onNavigateToProductoresList
                        )
                    }
                    item {
                        QuickActionChip(
                            icon = Icons.Filled.ShoppingCart,
                            label = "Productos",
                            onClick = onNavigateToProductosList
                        )
                    }
                    item {
                        QuickActionChip(
                            icon = Icons.Filled.ShoppingBag,
                            label = "Carrito",
                            onClick = onNavigateToCarrito
                        )
                    }
                    item {
                        QuickActionChip(
                            icon = Icons.Filled.ReceiptLong,
                            label = "Mis pedidos",
                            onClick = onNavigateToPedidos
                        )
                    }
                }
            }

            // ACCIONES ESPECIALES SEGÚN ROL (PRODUCTOR / ADMIN)
            if (currentRole?.equals("PRODUCTOR", true) == true ||
                currentRole?.equals("ADMIN", true) == true
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Acciones de productor",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {
                                QuickActionChip(
                                    icon = Icons.Filled.AddCircle,
                                    label = "Crear producto",
                                    onClick = onNavigateToCreateProducto
                                )
                            }
                            item {
                                QuickActionChip(
                                    icon = Icons.Filled.PersonAdd,
                                    label = "Crear productor",
                                    onClick = onNavigateToCreateProductor
                                )
                            }
                            item {
                                QuickActionChip(
                                    icon = Icons.Filled.Inventory,     // cajas / inventario
                                    label = "Mis productos",
                                    onClick = onNavigateToProductosList
                                )
                            }
                        }
                    }
                }
            }

            // PRODUCTORES DESTACADOS
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SectionHeader(
                        title = "Productores Destacados",
                        actionText = "Ver todos",
                        onActionClick = onNavigateToProductoresList
                    )
                    when (featuredProductoresState) {
                        is HomeUiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is HomeUiState.Error -> {
                            Text(
                                text = "No se pudieron cargar los productores",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        is HomeUiState.Success -> {
                            val productores =
                                (featuredProductoresState as HomeUiState.Success<List<com.example.saborlocalspa.model.Productor>>).data
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(productores.size) { index ->
                                    val productor = productores[index]
                                    ProductorCard(
                                        productor = productor,
                                        onClick = onNavigateToProductoresList
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // PRODUCTOS RECIENTES
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    SectionHeader(
                        title = "Productos",
                        actionText = "Ver todos",
                        onActionClick = onNavigateToProductosList
                    )
                    when (recentProductsState) {
                        is HomeUiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is HomeUiState.Error -> {
                            Text(
                                text = "No se pudieron cargar los productos",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        is HomeUiState.Success -> {
                            val productos =
                                (recentProductsState as HomeUiState.Success<List<Producto>>).data
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(productos.size) { index ->
                                    val producto = productos[index]
                                    HomeProductoCard(
                                        producto = producto,
                                        onClick = { selectedProduct = producto }
                                    )
                                }
                            }
                        }
                    }
                }
            }

// CATEGORÍAS
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Explorar Categorías",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Fila 1
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryCard(
                            icon = Icons.Filled.Eco,
                            title = "Especias",
                            count = "80 productos",
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToProductosList
                        )
                        CategoryCard(
                            icon = Icons.Filled.BreakfastDining,
                            title = "Mermeladas",
                            count = "30 productos",
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToProductosList
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Fila 2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryCard(
                            icon = Icons.Filled.BreakfastDining,
                            title = "Lácteos",
                            count = "45 productos",
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToProductosList
                        )

                        CategoryCard(
                            icon = Icons.Filled.LocalCafe,      // ícono de café
                            title = "Café",
                            count = "20 productos",
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToProductosList
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Fila 3
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryCard(
                            icon = Icons.Filled.BakeryDining,
                            title = "Panadería",
                            count = "60 productos",
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToProductosList
                        )
                        CategoryCard(
                            icon = Icons.Filled.RiceBowl,
                            title = "Frutos secos",
                            count = "25 productos",
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToProductosList
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
