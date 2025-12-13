package com.example.saborlocalspa.data.remote.dto.pedido

data class PedidoItemDto(
    val producto: String,  // ✅ Backend retorna "producto" (String ID), no "productoId"
    val cantidad: Int,
    val precio: Double
)
