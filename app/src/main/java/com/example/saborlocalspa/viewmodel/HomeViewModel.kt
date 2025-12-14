package com.example.saborlocalspa.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.saborlocalspa.data.local.TokenManager
import com.example.saborlocalspa.model.Producto
import com.example.saborlocalspa.model.ApiResult
import com.example.saborlocalspa.model.Productor
import com.example.saborlocalspa.repository.ProductoRepository
import com.example.saborlocalspa.repository.ProductorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val productoRepository = ProductoRepository()
    private val productorRepository = ProductorRepository()
    private val tokenManager = TokenManager(application)

    private val _recentProducts = MutableStateFlow<HomeUiState<List<Producto>>>(HomeUiState.Loading)
    val recentProducts: StateFlow<HomeUiState<List<Producto>>> = _recentProducts.asStateFlow()

    private val _featuredProductores = MutableStateFlow<HomeUiState<List<Productor>>>(HomeUiState.Loading)
    val featuredProductores: StateFlow<HomeUiState<List<Productor>>> = _featuredProductores.asStateFlow()

    // Rol actual del usuario (CLIENTE, PRODUCTOR, ADMIN, REPARTIDOR...)
    private val _currentRole = MutableStateFlow<String?>(null)
    val currentRole: StateFlow<String?> = _currentRole.asStateFlow()

    init {
        loadUserRole()
        loadHomeData()
    }

    private fun loadUserRole() {
        // TokenManager es síncrono, se puede leer directo
        val user = tokenManager.getCurrentUser()
        _currentRole.value = user?.role
    }

    fun loadHomeData() {
        loadRecentProducts()
        loadFeaturedProductores()
    }

    private fun loadRecentProducts() {
        _recentProducts.value = HomeUiState.Loading
        viewModelScope.launch {
            when (val result = productoRepository.getProductos()) {
                is ApiResult.Success -> {
                    val products = result.data.take(10)
                    _recentProducts.value = HomeUiState.Success(products)
                }
                is ApiResult.Error -> {
                    _recentProducts.value = HomeUiState.Error(result.message)
                }
            }
        }
    }

    private fun loadFeaturedProductores() {
        _featuredProductores.value = HomeUiState.Loading
        viewModelScope.launch {
            when (val result = productorRepository.getProductores()) {
                is ApiResult.Success -> {
                    val productores = result.data.take(5)
                    _featuredProductores.value = HomeUiState.Success(productores)
                }
                is ApiResult.Error -> {
                    _featuredProductores.value = HomeUiState.Error(result.message)
                }
            }
        }
    }
}

sealed class HomeUiState<out T> {
    object Loading : HomeUiState<Nothing>()
    data class Success<T>(val data: T) : HomeUiState<T>()
    data class Error(val message: String) : HomeUiState<Nothing>()
}
