package com.example.saborlocalspa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.saborlocalspa.data.remote.RetrofitClient
import com.example.saborlocalspa.ui.navigation.AppNavigation
import com.example.saborlocalspa.ui.theme.MiAppModularTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar RetrofitClient con el contexto de la aplicación
        // Esto configura TokenManager (EncryptedSharedPreferences) para tokens seguros
        RetrofitClient.initialize(this)

        // Configurar barras del sistema ANTES de setContent
        window.statusBarColor = android.graphics.Color.WHITE
        window.navigationBarColor = android.graphics.Color.WHITE

        // Iconos oscuros en las barras (para fondo blanco)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        setContent {
            MiAppModularTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
