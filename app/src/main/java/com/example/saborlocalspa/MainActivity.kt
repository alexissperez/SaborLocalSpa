package com.example.saborlocalspa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.saborlocalspa.ui.navigation.AppNavigation
import com.example.saborlocalspa.ui.theme.SaborLocalSpaTheme

class MainActivity : ComponentActivity() {

    // Instancia global singleton de dependencias
    private lateinit var appDependencies: AppDependencies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la instancia singleton usando el contexto Application
        appDependencies = AppDependencies.getInstance(application)

        setContent {
            SaborLocalSpaTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    // Llama la navegación con NavController y tus dependencias
                    AppNavigation(navController, appDependencies)
                }
            }
        }
    }
}
