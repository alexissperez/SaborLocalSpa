package com.example.saborlocalspa.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para la respuesta de login.
 * Contiene los datos que RECIBIMOS del servidor tras un login exitoso.
 */
data class LoginResponse(
    @SerializedName("id")
    val id: Int, // ID único del usuario

    @SerializedName("username")
    val username: String, // Nombre de usuario

    @SerializedName("email")
    val email: String, // Correo electrónico

    @SerializedName("firstName")
    val firstName: String, // Primer nombre del usuario

    @SerializedName("lastName")
    val lastName: String, // Apellido del usuario

    @SerializedName("accessToken")
    val accessToken: String,  // 🔑 TOKEN JWT - Debe guardarse en SessionManager para futuras llamadas

    @SerializedName("refreshToken")
    val refreshToken: String?  // Token para renovar el acceso, puede ser nulo/ausente según la API
)
