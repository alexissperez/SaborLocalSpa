package com.example.saborlocalspa.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para la solicitud de login.
 * Datos que ENVÍA la app al servidor para autenticar al usuario.
 */
data class LoginRequest(
    @SerializedName("username")
    val username: String, // Nombre de usuario enviado al backend

    @SerializedName("password")
    val password: String,  // Contraseña enviada al backend

    @SerializedName("caduca en minutos")
    val caducaEnMinutos: Int = 30  // Token expira en 30 minutos, nombre sin espacios en Kotlin
)
