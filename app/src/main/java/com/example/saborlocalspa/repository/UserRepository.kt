package com.example.saborlocalspa.repository

import android.content.Context
import com.example.saborlocalspa.data.remote.ApiService
import com.example.saborlocalspa.data.remote.RetrofitClient
import com.example.saborlocalspa.data.remote.dto.UserDto

/**
 * Repository: Abstrae la fuente de datos.
 * El ViewModel NO sabe si los datos vienen de API, base de datos local, etc.
 */
class UserRepository(context: Context) {

    // Crear la instancia del API Service usando el contexto
    private val apiService: ApiService = RetrofitClient
        .create(context)
        .create(ApiService::class.java)

    /**
     * Obtiene un usuario de la API.
     *
     * Usa Result<T> para manejar éxito/error de forma elegante.
     */
    suspend fun fetchUser(id: Int = 1): Result<UserDto> {
        return try {
            // Llama al endpoint de la API (puede demorar por red)
            val user = apiService.getUserById(id)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
