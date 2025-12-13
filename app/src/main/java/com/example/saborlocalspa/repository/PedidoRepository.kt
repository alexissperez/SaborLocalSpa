package com.example.saborlocalspa.repository

import com.example.saborlocalspa.data.mapper.toModel
import com.example.saborlocalspa.data.remote.RetrofitClient
import com.example.saborlocalspa.data.remote.dto.pedido.CreatePedidoRequest
import com.example.saborlocalspa.model.Pedido
import com.example.saborlocalspa.model.ApiResult

class PedidoRepository {

    private val apiService = RetrofitClient.saborLocalPedidoApiService

    suspend fun getAllPedidos(): ApiResult<List<Pedido>> {
        return try {
            val response = apiService.getPedidos()

            if (response.isSuccessful) {
                val apiResponse = response.body()

                if (apiResponse?.success == true && apiResponse.data != null) {
                    // Convertir lista de DTOs a modelos de dominio, filtrando nulls
                    val pedidos = apiResponse.data.mapNotNull { it.toModel() }
                    ApiResult.Success(pedidos)
                } else {
                    ApiResult.Error(apiResponse?.message ?: "Error al obtener pedidos")
                }
            } else {
                ApiResult.Error("Error en el servidor: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido", e)
        }
    }

    suspend fun getPedidosByCliente(clienteId: String): ApiResult<List<Pedido>> {
        return try {
            val response = apiService.getPedidosByCliente(clienteId)

            if (response.isSuccessful) {
                val apiResponse = response.body()

                if (apiResponse?.success == true && apiResponse.data != null) {
                    // Convertir lista de DTOs a modelos de dominio, filtrando nulls
                    val pedidos = apiResponse.data.mapNotNull { it.toModel() }
                    ApiResult.Success(pedidos)
                } else {
                    ApiResult.Error(apiResponse?.message ?: "Error al obtener pedidos del cliente")
                }
            } else {
                ApiResult.Error("Error en el servidor: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido", e)
        }
    }

    /**
     * Obtiene un pedido específico por ID
     *
     * @param id ID del pedido
     * @return Result con el pedido o error
     */
    suspend fun getPedido(id: String): ApiResult<Pedido> {
        return try {
            val response = apiService.getPedido(id)

            if (response.isSuccessful) {
                val apiResponse = response.body()

                if (apiResponse?.success == true && apiResponse.data != null) {
                    val pedido = apiResponse.data.toModel()

                    if (pedido != null) {
                        ApiResult.Success(pedido)
                    } else {
                        ApiResult.Error("Error al convertir el pedido: datos incompletos del backend")
                    }
                } else {
                    ApiResult.Error(apiResponse?.message ?: "Error al obtener el pedido")
                }
            } else {
                ApiResult.Error("Error en el servidor: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido", e)
        }
    }

    suspend fun createPedido(request: CreatePedidoRequest): ApiResult<Pedido> {
        return try {
            val response = apiService.createPedido(request)

            if (response.isSuccessful) {
                val apiResponse = response.body()

                if (apiResponse?.success == true && apiResponse.data != null) {
                    val pedido = apiResponse.data.toModel()

                    if (pedido != null) {
                        ApiResult.Success(pedido)
                    } else {
                        ApiResult.Error("Error al convertir el pedido: datos incompletos del backend")
                    }
                } else {
                    ApiResult.Error(apiResponse?.message ?: "Error al crear el pedido")
                }
            } else {
                ApiResult.Error("Error en el servidor: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido", e)
        }
    }

    suspend fun updateEstadoPedido(id: String, nuevoEstado: String): ApiResult<Pedido> {
        return try {
            val request = mapOf("estado" to nuevoEstado)
            val response = apiService.updatePedido(id, request)

            if (response.isSuccessful) {
                val apiResponse = response.body()

                if (apiResponse?.success == true && apiResponse.data != null) {
                    val pedido = apiResponse.data.toModel()

                    if (pedido != null) {
                        ApiResult.Success(pedido)
                    } else {
                        ApiResult.Error("Error al convertir el pedido: datos incompletos del backend")
                    }
                } else {
                    ApiResult.Error(apiResponse?.message ?: "Error al actualizar el pedido")
                }
            } else {
                ApiResult.Error("Error en el servidor: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido", e)
        }
    }

    suspend fun deletePedido(id: String): ApiResult<Unit> {
        return try {
            val response = apiService.deletePedido(id)

            if (response.isSuccessful) {
                val apiResponse = response.body()

                if (apiResponse?.success == true) {
                    ApiResult.Success(Unit)
                } else {
                    ApiResult.Error(apiResponse?.message ?: "Error al eliminar el pedido")
                }
            } else {
                ApiResult.Error("Error en el servidor: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error desconocido", e)
        }
    }
}
