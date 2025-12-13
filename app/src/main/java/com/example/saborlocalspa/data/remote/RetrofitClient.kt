package com.example.saborlocalspa.data.remote

import android.content.Context
import com.example.saborlocalspa.data.local.TokenManager
import com.example.saborlocalspa.data.remote.api.*
import com.example.saborlocalspa.data.remote.dto.pedido.ClienteDto
import com.example.saborlocalspa.data.remote.dto.pedido.ClienteDtoDeserializer
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @see TokenManager
 * @see AuthInterceptor
 */
object RetrofitClient {

    private const val BASE_URL = "https://saborloca-api.onrender.com/api/"

    private lateinit var tokenManager: TokenManager

    fun initialize(context: Context) {
        tokenManager = TokenManager(context.applicationContext)
    }

    private val okHttpClient: OkHttpClient by lazy {
        // AuthInterceptor añade el token JWT a las peticiones automáticamente
        val authInterceptor = AuthInterceptor(tokenManager)

        // LoggingInterceptor registra las peticiones y respuestas para debugging
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)  // Aumentado para cold starts de Render
            .readTimeout(90, TimeUnit.SECONDS)     // Aumentado para operaciones lentas
            .writeTimeout(60, TimeUnit.SECONDS)    // Aumentado para uploads grandes
            .build()
    }

    private val gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(ClienteDto::class.java, ClienteDtoDeserializer())
            .create()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // ========= API Services - Organizados por dominio =========

    /**
     * API service para autenticación (login, registro, perfil)
     */
    val saborLocalAuthApiService: SaborLocalAuthApiService by lazy {
        retrofit.create(SaborLocalAuthApiService::class.java)
    }

    /**
     * API service para gestión de productos
     */
    val saborLocalProductoApiService: SaborLocalProductoApiService by lazy {
        retrofit.create(SaborLocalProductoApiService::class.java)
    }

    /**
     * API service para gestión de productores
     */
    val saborLocalProductorApiService: SaborLocalProductorApiService by lazy {
        retrofit.create(SaborLocalProductorApiService::class.java)
    }

    /**
     * API service para gestión de clientes
     */
    val saborLocalClienteApiService: SaborLocalClienteApiService by lazy {
        retrofit.create(SaborLocalClienteApiService::class.java)
    }

    /**
     * API service para gestión de pedidos
     */
    val saborLocalPedidoApiService: SaborLocalPedidoApiService by lazy {
        retrofit.create(SaborLocalPedidoApiService::class.java)
    }

    /**
     * API service para gestión de entregas
     */
    val saborLocalEntregaApiService: SaborLocalEntregaApiService by lazy {
        retrofit.create(SaborLocalEntregaApiService::class.java)
    }

    /**
     * API service para subir archivos (imágenes, documentos)
     */
    val saborLocalUploadApiService: SaborLocalUploadApiService by lazy {
        retrofit.create(SaborLocalUploadApiService::class.java)
    }

    fun getTokenManager(): TokenManager = tokenManager
}
