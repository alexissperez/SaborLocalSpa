package com.example.saborlocalspa.data.remote  // Usa tu paquete real aquí

import com.example.saborlocalspa.data.local.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * AuthInterceptor: Añade automáticamente el token JWT a las peticiones HTTP.
 *
 * Funcionamiento:
 * - Se ejecuta ANTES de cada petición HTTP realizada por Retrofit.
 * - Recupera el token JWT almacenado en el SessionManager.
 * - Si el token existe, agrega el header Authorization: Bearer {token}.
 * - Si no existe, la petición sigue normal SIN modificar headers.
 */
class AuthInterceptor(
    private val sessionManager: SessionManager
) : Interceptor {

    // Intercepta todas las peticiones HTTP
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Recupera el token usando runBlocking porque intercept no es suspend
        val token = runBlocking {
            sessionManager.getAuthToken()
        }

        // Si no hay token, no modifica la petición, solo continúa
        if (token.isNullOrEmpty()) {
            return chain.proceed(originalRequest)
        }

        // Si hay token, crea una nueva petición con el header Authorization
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        // Continúa la cadena con la nueva petición autenticada
        return chain.proceed(authenticatedRequest)
    }
}
