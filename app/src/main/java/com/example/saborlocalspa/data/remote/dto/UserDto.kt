package com.example.saborlocalspa.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO = Data Transfer Object
 * Este objeto representa los datos que VIAJAN entre tu app y el servidor.
 * Se usa en las respuestas y solicitudes de la API.
 */
data class UserDto(
    @SerializedName("id")
    val id: Int, // ID del usuario en el servidor

    @SerializedName("username")
    val username: String, // Nombre de usuario

    @SerializedName("email")
    val email: String, // Email del usuario

    @SerializedName("firstName")
    val firstName: String, // Primer nombre

    @SerializedName("lastName")
    val lastName: String, // Apellido

    @SerializedName("image")
    val image: String? = null // URL de la imagen de perfil (puede ser nula, es opcional)
)
