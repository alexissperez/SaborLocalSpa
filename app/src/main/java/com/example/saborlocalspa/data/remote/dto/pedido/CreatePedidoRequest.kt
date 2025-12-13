package com.example.saborlocalspa.data.remote.dto.pedido

data class CreatePedidoRequest(
    val items: List<PedidoItemRequest>,
    val direccionEntrega: String? = null,
    val notasEntrega: String? = null
)
