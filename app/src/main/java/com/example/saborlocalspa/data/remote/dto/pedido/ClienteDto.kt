package com.example.saborlocalspa.data.remote.dto.pedido

import com.google.gson.annotations.SerializedName

data class ClienteDto(
    @SerializedName("_id")
    val id: String,
    val nombre: String? = null,  // Nombre del cliente (puede ser null)
    val email: String? = null,  // Email (opcional si no lo incluye el backend)
    val telefono: String? = null,  // Teléfono (opcional)
    val direccion: String? = null  // Dirección (opcional)
)
