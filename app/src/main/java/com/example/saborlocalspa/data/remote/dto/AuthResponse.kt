package com.example.saborlocalspa.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * ```
 *
 * @property authToken Token JWT de autenticación. Se usa para autorizar peticiones
 *                     subsecuentes a la API. Nullable si falla la autenticación.
 * @property id Identificador único del usuario en la base de datos de Xano.
 *              Nullable si la respuesta está incompleta.
 * @property name Nombre completo del usuario registrado en el sistema.
 *                Nullable si no se proporciona.
 * @property email Dirección de correo electrónico del usuario autenticado.
 *                 Nullable si no se proporciona.
 * @property createdAt Timestamp Unix (milisegundos) de la fecha de creación de la cuenta.
 *                     Se puede convertir a Date con `Date(createdAt)`.
 *                     Nullable si no se proporciona.
 * @property roleUser Rol del usuario en el sistema (ej: "admin", "user", "moderator").
 *                    Define permisos y nivel de acceso del usuario. Nullable si no
 *                    se especifica o si el backend no implementa roles.
 *
 * @see com.example.saborlocalspa.model.dto.LoginRequest
 * @see com.example.saborlocalspa.model.dto.SignUpRequest
 * @see com.example.saborlocalspa.network.AuthApiService
 * @see com.example.saborlocalspa.model.SessionManager
 */
data class AuthResponse(
    @SerializedName("authToken")
    val authToken: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("created_at")
    val createdAt: Long?,

    @SerializedName("role")
    val roleUser : String?
)