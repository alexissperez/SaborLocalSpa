package com.example.saborlocalspa.data.remote.dto.producto

/**
 * Request para crear un nuevo Producto
 */
data class CreateProductoRequest(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val unidad: String,
    val stock: Int,
    val categoria: String,          // p.ej. "Lacteos", "Conservas"
    val disponible: Boolean = true, // por defecto disponible
    val imagen: String? = null,     // path como "uploads/....png" (opcional)
    val imagenThumbnail: String? = null, // path thumbnail (opcional)
    val productor: String           // ID del productor
)
