package com.example.saborlocalspa.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.saborlocalspa.data.local.dao.UserDao
import com.example.saborlocalspa.data.entity.User

// RoomDatabase principal de la app, referencia tus DAOs y entidades aquí
@Database(
    entities = [User::class], // Puedes agregar más entidades separadas por coma
    version = 1,              // Cambia el número si migras la BD
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // Acceso a tablas/DAO
    abstract fun userDao(): UserDao
}
