package com.example.saborlocalspa.data.remote.dto.pedido

import com.google.gson.annotations.SerializedName

data class PedidoDto(
    @SerializedName("_id")
    val id: String,
    val cliente: ClienteDto,  // ✅ Backend retorna objeto completo (con .populate())
    val items: List<PedidoItemDto>,  // ✅ Backend retorna "items", no "productos"
    val total: Double,
    val estado: String,  // pendiente, en_preparacion, en_camino, entregado, cancelado
    val direccionEntrega: String? = null,  // String opcional
    val notasEntrega: String? = null,  // String opcional
    val createdAt: String? = null,  // ✅ Backend usa createdAt como fecha del pedido
    val updatedAt: String? = null
)

