package com.example.saborlocalspa.data.mapper

import com.example.saborlocalspa.data.remote.dto.producto.ProductoDto
import com.example.saborlocalspa.data.remote.dto.productor.ProductorDto
import com.example.saborlocalspa.model.Producto
import com.example.saborlocalspa.model.Productor

private const val IMAGE_BASE_URL = "https://saborlocal-api.onrender.com"

private fun buildImageUrl(path: String?): String? {
    if (path.isNullOrBlank()) return null
    return if (path.startsWith("http")) path else IMAGE_BASE_URL + path
}

fun ProductoDto.toModel(): Producto {
    return Producto(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        precio = precio,
        unidad = unidad,
        stock = stock,
        productor = Productor(
            id = getProductorId(),
            nombre = null,
            ubicacion = null,
            telefono = null,
            email = null
        ),
        // usa SOLO el campo que sí viene en el JSON
        imagen = buildImageUrl(imagen ?: imagenThumbnail),
        imagenThumbnail = null
    )
}

/**
 * Convierte ProductoDto a Producto con datos completos del productor
 *
 * Usa esta función cuando ya tengas los datos del productor.
 *
 * @param productorData Datos completos del productor
 */
fun ProductoDto.toModelWithProductor(productorData: Productor): Producto {
    return Producto(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        precio = precio,
        unidad = unidad,
        stock = stock,
        productor = productorData,
        imagen = imagen,
        imagenThumbnail = imagenThumbnail
    )
}

/**
 * Convierte ProductorDto a Productor (modelo de dominio)
 */
fun ProductorDto.toModel(): Productor {
    return Productor(
        id = id,
        nombre = nombre,
        ubicacion = ubicacion ?: "",
        telefono = telefono ?: "",
        email = email ?: "",
        imagen = imagen,
        imagenThumbnail = imagenThumbnail
    )
}
