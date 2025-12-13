package com.example.saborlocalspa.repository

import com.example.saborlocalspa.data.remote.RetrofitClient
import com.example.saborlocalspa.data.remote.dto.auth.LoginSaborLocalRequest
import com.example.saborlocalspa.data.remote.dto.auth.RegisterSaborLocalRequest
import com.example.saborlocalspa.model.User
import com.example.saborlocalspa.model.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthSaborLocalRepository {

    // Acceso directo a las dependencias desde RetrofitClient
    private val tokenManager = RetrofitClient.getTokenManager()
    private val apiService = RetrofitClient.saborLocalAuthApiService

    /**
     * Verifica si hay una sesión activa
     */
    fun isLoggedIn(): Boolean {
        return tokenManager.isLoggedIn()
    }

    /**
     * Obtiene el token JWT guardado
     */
    fun getToken(): String? {
        return tokenManager.getToken()
    }

    /**
     * Guarda el token y datos del usuario usando TokenManager
     */
    private fun saveSession(token: String, user: com.example.saborlocalspa.data.remote.dto.auth.UserDto) {
        tokenManager.saveToken(token, user)
    }

    /**
     * Obtiene el usuario guardado en sesión
     */
    fun getCurrentUser(): User? {
        return tokenManager.getCurrentUser()
    }

    /**
     * Cierra sesión (elimina token y datos)
     */
    fun logout() {
        tokenManager.clearToken()
    }

    /**
     * Login de usuario
     *
     * @param email Email del usuario
     * @param password Contraseña
     * @return ApiResult con el usuario autenticado
     */
    suspend fun login(email: String, password: String): ApiResult<User> = withContext(Dispatchers.IO) {
        try {
            val request = LoginSaborLocalRequest(email, password)
            val response = apiService.login(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val authData = body.data
                    saveSession(authData.accessToken, authData.user)

                    val user = User(
                        id = authData.user.id,
                        nombre = authData.user.nombre,
                        email = authData.user.email,
                        role = authData.user.role,
                        telefono = authData.user.telefono,
                        ubicacion = authData.user.ubicacion,
                        direccion = authData.user.direccion
                    )
                    ApiResult.Success(user)
                } else {
                    ApiResult.Error(body?.message ?: "Error en login")
                }
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Credenciales inválidas"
                    404 -> "Usuario no encontrado"
                    else -> "Error HTTP ${response.code()}"
                }
                ApiResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }

    suspend fun register(
        email: String,
        password: String,
        role: String = "CLIENTE",
        nombre: String,
        telefono: String? = null,
        direccion: String? = null,
        nombreNegocio: String? = null,
        descripcion: String? = null
    ): ApiResult<User> = withContext(Dispatchers.IO) {
        try {
            val request = RegisterSaborLocalRequest(
                email = email,
                password = password,
                role = role,
                nombre = nombre,
                telefono = telefono,
                direccion = direccion,
                nombreNegocio = nombreNegocio,
                descripcion = descripcion
            )
            val response = apiService.register(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val authData = body.data
                    saveSession(authData.accessToken, authData.user)

                    val user = User(
                        id = authData.user.id,
                        nombre = authData.user.nombre,
                        email = authData.user.email,
                        role = authData.user.role,
                        telefono = authData.user.telefono,
                        ubicacion = authData.user.ubicacion,
                        direccion = authData.user.direccion
                    )
                    ApiResult.Success(user)
                } else {
                    ApiResult.Error(body?.message ?: "Error en registro")
                }
            } else {
                val errorMessage = when (response.code()) {
                    409 -> "El email ya está registrado"
                    400 -> "Datos inválidos"
                    else -> "Error HTTP ${response.code()}"
                }
                ApiResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }


    suspend fun createProductorUser(
        nombre: String,
        email: String,
        password: String,
        ubicacion: String,
        telefono: String
    ): ApiResult<User> = withContext(Dispatchers.IO) {
        try {
            val request = com.example.saborlocalspa.data.remote.dto.auth.CreateProductorUserRequest(
                nombre = nombre,
                email = email,
                password = password,
                ubicacion = ubicacion,
                telefono = telefono
            )
            val response = apiService.createProductorUser(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val authData = body.data
                    // No guardamos la sesión porque el ADMIN sigue logueado

                    val user = User(
                        id = authData.user.id,
                        nombre = authData.user.nombre,
                        email = authData.user.email,
                        role = authData.user.role,
                        telefono = authData.user.telefono,
                        ubicacion = authData.user.ubicacion,
                        direccion = authData.user.direccion
                    )
                    ApiResult.Success(user)
                } else {
                    ApiResult.Error(body?.message ?: "Error al crear productor")
                }
            } else {
                val errorMessage = when (response.code()) {
                    409 -> "El email ya está registrado"
                    403 -> "No tienes permisos para crear productores (requiere rol ADMIN)"
                    400 -> "Datos inválidos"
                    else -> "Error HTTP ${response.code()}"
                }
                ApiResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }

    /**
     * Obtiene el perfil del usuario actual
     *
     * Requiere estar autenticado (token en headers)
     *
     * @return ApiResult con el usuario actualizado
     */
    suspend fun getProfile(): ApiResult<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProfile()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val userDto = body.data

                    // Actualizar datos del usuario en TokenManager
                    tokenManager.updateUserData(userDto)

                    val user = User(
                        id = userDto.id,
                        nombre = userDto.nombre,
                        email = userDto.email,
                        role = userDto.role,
                        telefono = userDto.telefono,
                        ubicacion = userDto.ubicacion,
                        direccion = userDto.direccion
                    )
                    ApiResult.Success(user)
                } else {
                    ApiResult.Error(body?.message ?: "Error obteniendo perfil")
                }
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Sesión expirada"
                    else -> "Error HTTP ${response.code()}"
                }
                ApiResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }

    /**
     * Obtiene la lista de todos los usuarios (solo ADMIN)
     *
     * Requiere estar autenticado con rol ADMIN
     *
     * @return ApiResult con la lista de usuarios
     */
    suspend fun getAllUsers(): ApiResult<List<User>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllUsers()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val users = body.data.map { userDto ->
                        User(
                            id = userDto.id,
                            nombre = userDto.nombre,
                            email = userDto.email,
                            role = userDto.role,
                            telefono = userDto.telefono,
                            ubicacion = userDto.ubicacion,
                            direccion = userDto.direccion
                        )
                    }
                    ApiResult.Success(users)
                } else {
                    ApiResult.Error(body?.message ?: "Error obteniendo usuarios")
                }
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Sesión expirada"
                    403 -> "No tienes permisos para ver usuarios (requiere rol ADMIN)"
                    else -> "Error HTTP ${response.code()}"
                }
                ApiResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }
}
