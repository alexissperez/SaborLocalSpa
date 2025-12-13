package com.example.saborlocalspa.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 *
 * @property name Nombre completo del usuario. Debe contener al menos 3 caracteres.
 * @property email Dirección de correo electrónico del usuario. Debe ser un email válido
 *                 y único en el sistema.
 * @property password Contraseña del usuario. Debe cumplir con los requisitos de seguridad:
 *                    mínimo 6 caracteres, al menos una letra mayúscula, una minúscula y un número.
 *
 * @see com.example.saborlocalspa.model.dto.SignUpResponse
 * @see com.example.saborlocalspa.network.ApiService.signUp
 */
data class SignUpRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)
