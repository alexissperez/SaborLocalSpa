package com.example.saborlocalspa.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("_id")
    val id: String,
    val nombre: String? = null,  // ✅ Nullable - algunos usuarios no lo tienen
    val email: String,
    val role: String,  // CLIENTE, PRODUCTOR, ADMIN
    val telefono: String? = null,
    val direccion: String? = null,
    val ubicacion: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)


