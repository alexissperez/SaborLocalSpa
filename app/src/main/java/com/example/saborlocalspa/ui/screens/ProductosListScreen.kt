package com.example.saborlocalspa.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.saborlocalspa.data.local.CarritoManager
import com.example.saborlocalspa.model.Producto
import com.example.saborlocalspa.ui.components.FiltersBottomSheet
import com.example.saborlocalspa.ui.components.ProductDetailSheet
import com.example.saborlocalspa.ui.components.StandardScaffold
import com.example.saborlocalspa.viewmodel.ProductoViewModel
import com.example.saborlocalspa.viewmodel.ProductosListUiState
import com.example.saborlocalspa.viewmodel.ProductosListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosListScreen(
    viewModel: ProductosListViewModel = viewModel(),
    productoViewModel: ProductoViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onProductClick: (String) -> Unit = {},
    currentRole: String = ""          // para saber si es PRODUCTOR / ADMIN
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedProductor by viewModel.selectedProductor.collectAsState()
    val minPrice by viewModel.minPrice.collectAsState()
    val maxPrice by viewModel.maxPrice.collectAsState()

    var showFilters by remember { mutableStateOf(false) }
    var showProductorMenu by remember { mutableStateOf(false) }

    // Estado para el detalle de producto (Bottom Sheet)
    var selectedProduct by remember { mutableStateOf<Producto?>(null) }

    val context = androidx.compose.ui.platform.LocalContext.current

// Bottom Sheet de detalle (ACTUALIZADO)
    if (selectedProduct != null) {
        val esProductor =
            currentRole.equals("PRODUCTOR", ignoreCase = true) ||
                    currentRole.equals("Productor", ignoreCase = true) ||
                    currentRole.equals("ADMIN", ignoreCase = true)

        ProductDetailSheet(
            producto = selectedProduct!!,
            esProductor = esProductor,
            onDismissRequest = { selectedProduct = null },
            onAddToCart = { producto ->
                CarritoManager.addItem(producto)
                android.widget.Toast.makeText(
                    context,
                    "Producto agregado al carrito",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                selectedProduct = null
            },
            onDelete = { producto ->
                productoViewModel.deleteProducto(producto.id)
                viewModel.loadProductos()   // recarga la lista del grid
                android.widget.Toast.makeText(
                    context,
                    "Producto eliminado",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                selectedProduct = null
            }
        )
    }

    // Bottom Sheet de Filtros
    if (showFilters) {
        FiltersBottomSheet(
            onDismissRequest = { showFilters = false },
            onApplyFilters = {
                showFilters = false
            },
            minPrice = minPrice,
            maxPrice = maxPrice,
            onPriceRangeChange = { range ->
                viewModel.onMinPriceChange(range.start.toDouble())
                viewModel.onMaxPriceChange(range.endInclusive.toDouble())
            }
        )
    }

    StandardScaffold(
        title = "Productos",
        onNavigateBack = onNavigateBack,
        actionContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { showFilters = !showFilters }) {
                    Icon(
                        if (showFilters) Icons.Default.FilterAltOff else Icons.Default.FilterAlt,
                        contentDescription = "Filtros"
                    )
                }
                IconButton(onClick = { viewModel.loadProductos() }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar productos...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            // Contenido principal
            when (val state = uiState) {
                is ProductosListUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ProductosListUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                Icons.Default.Error,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadProductos() }) {
                                Text("Reintentar")
                            }
                        }
                    }
                }

                is ProductosListUiState.Empty -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                Icons.Default.ShoppingBag,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                is ProductosListUiState.Success -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "${state.productos.size} Productos\nDisponibles",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            lineHeight = MaterialTheme.typography.headlineMedium.fontSize * 1.1f
                        )

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 100.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.productos) { producto ->
                                ProductoCardGrid(
                                    producto = producto,
                                    onClick = { selectedProduct = producto }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoCardGrid(
    producto: Producto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = Color(0x1A000000)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                )

                AsyncImage(
                    model = producto.imagenThumbnail ?: producto.imagen,
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, bottom = 8.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF1A1A1A)
                )

                Text(
                    text = producto.productor.getDisplayName(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${producto.precio.toInt()}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Surface(
                        shape = CircleShape,
                        color = if (producto.stock > 0) MaterialTheme.colorScheme.primary else Color.LightGray,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
