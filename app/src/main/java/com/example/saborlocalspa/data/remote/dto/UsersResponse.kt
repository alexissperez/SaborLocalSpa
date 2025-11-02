package com.example.saborlocalspa.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO = Data Transfer Object
 * Este objeto representa los datos que VIAJAN entre tu app y el servidor.
 * Se usa en las respuestas y solicitudes de la API, y para mapear usuarios.
 */
data class UserDto(
    @SerializedName("id")
    val id: Int, // ID único del usuario en el backend

    @SerializedName("username")
    val username: String, // Nombre de usuario del backend

    @SerializedName("email")
    val email: String, // Email

    @SerializedName("firstName")
    val firstName: String, // Primer nombre

    @SerializedName("lastName")
    val lastName: String, // Apellido

    @SerializedName("image")
    val image: String? = null  // URL de imagen de perfil (opcional/nula)
)
