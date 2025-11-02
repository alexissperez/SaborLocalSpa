package com.example.saborlocalspa.data.remote // Usa tu paquete real aquí

import android.content.Context
import com.example.saborlocalspa.data.local.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Objeto singleton que gestiona la instancia de Retrofit.
 * Configura interceptores para autenticación y logging, y expone la creación con contexto.
 */
object RetrofitClient {

    // ⚠️ Cambia esta URL por la de tu API
    private const val BASE_URL = "https://dummyjson.com/"

    /**
     * Inicializa Retrofit con el contexto de la app.
     * Debes llamar a esta función desde una clase donde tengas el Context (Application, Activity, ViewModel).
     */
    fun create(context: Context): Retrofit {

        // 1️⃣ Instancia de SessionManager para recuperar el token JWT almacenado
        val sessionManager = SessionManager(context)

        // 2️⃣ Interceptor personalizado para agregar el token JWT a cada petición HTTP
        val authInterceptor = AuthInterceptor(sessionManager)

        // 3️⃣ Interceptor de logging para mostrar las solicitudes y respuestas en Logcat (útil en desarrollo, desactiva en producción)
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Usar NONE para producción
        }

        // 4️⃣ Cliente OkHttp configurado con ambos interceptores
        val okHttpClient = OkHttpClient.Builder()
            // El orden es importante: primero el interceptor de autenticación
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        // 5️⃣ Instancia de Retrofit con la configuración del cliente HTTP y el convertidor Gson para JSON
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}
