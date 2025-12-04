package com.example.saborlocalspa.data.remote.dto.auth

/**
 * Request para login en SaborLocal
 */
data class LoginSaborLocalRequest(
    val email: String,
    val password: String
)
