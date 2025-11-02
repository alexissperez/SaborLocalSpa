package com.example.saborlocalspa.data.remote

import com.example.saborlocalspa.data.remote.dto.*
import retrofit2.http.*

/**
 * ApiService:
 * Define los endpoints de tu API utilizando Retrofit.
 * Este ejemplo usa DummyJSON como referencia de API REST con autenticación JWT.
 */
interface ApiService {

    /**
     * 🔐 LOGIN - Autenticar usuario
     * POST /user/login
     *
     * Recibe un LoginRequest y retorna LoginResponse con el token y datos de usuario.
     * Ejemplo:
     * val response = apiService.login(LoginRequest("usuario", "contraseña"))
     * sessionManager.saveAuthToken(response.accessToken)
     */
    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
    /**
     * 👤 OBTENER USUARIO ACTUAL (requiere autenticación)
     * GET /user/me
     *
     * Este endpoint requiere token JWT, añadido por AuthInterceptor.
     * Ejemplo:
     * val currentUser = apiService.getCurrentUser()
     */
    @GET("user/me")
    suspend fun getCurrentUser(): UserDto

    /**
     * 📋 OBTENER LISTA DE USUARIOS
     * GET /users
     *
     * Ejemplo:
     * val response = apiService.getUsers()
     * val usersList = response.users
     */
    @GET("users")
    suspend fun getUsers(): UsersResponse

    /**
     * 🔍 BUSCAR USUARIOS POR NOMBRE
     * GET /users/search?q={query}
     *
     * Ejemplo:
     * val results = apiService.searchUsers("John")
     */
    @GET("users/search")
    suspend fun searchUsers(@Query("q") query: String): UsersResponse

    /**
     * 👤 OBTENER USUARIO POR ID
     * GET /users/{id}
     *
     * Ejemplo:
     * val user = apiService.getUserById(1)
     */
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDto
}
