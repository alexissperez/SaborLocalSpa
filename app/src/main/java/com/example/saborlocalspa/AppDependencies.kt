package com.example.saborlocalspa

import android.app.Application
import com.example.saborlocalspa.repository.AvatarRepository
import com.example.saborlocalspa.repository.UserRepository

// Agrega aquí los imports para tus otros repositorios o servicios
// import com.example.saborlocalspa.repository.UserRepository
// import com.example.saborlocalspa.data.SessionManager
// import com.example.saborlocalspa.data.AppDatabase
// import com.example.saborlocalspa.data.AuthApiService
// import com.example.saborlocalspa.data.UserDao

class AppDependencies private constructor(
    val avatarRepository: AvatarRepository,
    val userRepository: UserRepository, // <-- descomenta esta línea
    // ...otros repositorios
) {
    companion object {
        @Volatile
        private var INSTANCE: AppDependencies? = null

        fun getInstance(application: Application): AppDependencies {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppDependencies(
                    avatarRepository = AvatarRepository(application.applicationContext),
                    userRepository = UserRepository(application.applicationContext)
                    // ...otros repos
                ).also { INSTANCE = it }
            }
        }
    }
}

