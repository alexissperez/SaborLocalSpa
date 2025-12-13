package com.example.saborlocalspa.data.mapper

import com.example.saborlocalspa.data.remote.dto.pedido.PedidoDto
import com.example.saborlocalspa.data.remote.dto.pedido.PedidoItemDto
import com.example.saborlocalspa.model.*


fun PedidoItemDto.toModel(): PedidoItem {
    // Crear producto mínimo con los datos disponibles (solo ID y precio)
    val productoMinimo = Producto(
        id = producto,  // ✅ Ahora usa "producto" en lugar de "productoId"
        nombre = "Producto #$producto",  // ✅ No tenemos nombre, usar ID como fallback
        descripcion = "",
        precio = precio,
        unidad = "",
        stock = 0,
        productor = Productor(
            id = "",
            nombre = null,
            ubicacion = null,
            telefono = null,
            email = null,
            imagen = null,
            imagenThumbnail = null
        )
    )

    return PedidoItem(
        producto = productoMinimo,
        cantidad = cantidad,
        precio = precio
    )
}

/**
 * Convierte PedidoDto a Pedido (modelo de dominio)
 *
 * **Actualización:** El backend ahora retorna el cliente como objeto completo
 * usando `.populate('cliente')` en MongoDB.
 *
 * @return Pedido con datos disponibles del backend, o null si falta información crítica
 */
fun PedidoDto.toModel(): Pedido? {
    // Validar que tengamos la fecha (createdAt es requerida)
    val fecha = createdAt ?: return null

    // Usar el objeto cliente completo del backend
    val clienteObj = Cliente(
        id = cliente.id,
        nombre = cliente.nombre ?: "Cliente #${cliente.id}",  // Nombre real o fallback
        email = cliente.email ?: "",
        telefono = cliente.telefono ?: "",
        direccion = cliente.direccion ?: direccionEntrega ?: ""
    )

    // Convertir items
    val itemsObj = items.map { it.toModel() }

    // Convertir estado
    val estadoObj = EstadoPedido.fromString(estado)

    return Pedido(
        id = id,
        cliente = clienteObj,
        items = itemsObj,
        total = total,
        estado = estadoObj,
        fecha = fecha
    )
}
