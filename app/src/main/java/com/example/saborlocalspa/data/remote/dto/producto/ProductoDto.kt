package com.example.saborlocalspa.data.remote.dto.producto

import com.google.gson.annotations.SerializedName

data class ProductoDto(
    @SerializedName("_id")
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val unidad: String,  // kg, unidad, litro, etc.
    val categoria: String? = null,  // verduras, frutas, lacteos, etc.
    val productor: Any,  // Puede ser String (ID) o Map (objeto parcial/completo)
    val disponible: Boolean = true,
    val stock: Int,
    val imagen: String? = null,
    val imagenThumbnail: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    /**
     * Obtiene el ID del productor, sin importar el formato
     */
    fun getProductorId(): String {
        return when (productor) {
            is String -> productor
            is Map<*, *> -> (productor as Map<String, Any>)["_id"] as? String ?: ""
            else -> ""
        }
    }
}
