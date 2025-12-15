package com.example.saborlocalspa.repository

import android.util.Log
import com.example.saborlocalspa.data.mapper.toDomain
import com.example.saborlocalspa.data.mapper.toProductoDomainList
import com.example.saborlocalspa.data.remote.RetrofitClient
import com.example.saborlocalspa.data.remote.dto.producto.CreateProductoRequest
import com.example.saborlocalspa.data.remote.dto.producto.UpdateProductoRequest
import com.example.saborlocalspa.model.Producto
import com.example.saborlocalspa.model.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProductoRepository {

    // Acceso directo al API service desde RetrofitClient
    private val apiService = RetrofitClient.saborLocalProductoApiService

    suspend fun getProductos(): ApiResult<List<Producto>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProductos()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val productos = body.data.toProductoDomainList()
                    ApiResult.Success(productos)
                } else {
                    ApiResult.Error("No se pudieron obtener los productos")
                }
            } else {
                ApiResult.Error("Error HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Error al obtener productos", e)
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }

    suspend fun getProducto(id: String): ApiResult<Producto> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProducto(id)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val producto = body.data.toDomain()
                    ApiResult.Success(producto)
                } else {
                    ApiResult.Error("Producto no encontrado")
                }
            } else {
                ApiResult.Error("Error HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Error al obtener producto", e)
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }

    suspend fun getProductosByProductor(productorId: String): ApiResult<List<Producto>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProductosByProductor(productorId)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val productos = body.data.toProductoDomainList()
                    ApiResult.Success(productos)
                } else {
                    ApiResult.Error("No se pudieron obtener los productos del productor")
                }
            } else {
                ApiResult.Error("Error HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Error al obtener productos del productor", e)
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }

    suspend fun createProducto(
        nombre: String,
        descripcion: String,
        precio: Double,
        unidad: String,
        stock: Int,
        productorId: String
    ): ApiResult<Producto> = withContext(Dispatchers.IO) {
        try {
            val request = CreateProductoRequest(
                nombre = nombre,
                descripcion = descripcion,
                precio = precio,
                unidad = unidad,
                stock = stock,
                categoria = "Lacteos",     // o según la pantalla
                disponible = true,
                imagen = null,             // luego podrás actualizar con uploadImage
                imagenThumbnail = null,
                productor = productorId
            )

            val response = apiService.createProducto(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val producto = body.data.toDomain()
                    if (producto != null) {
                        ApiResult.Success(producto)
                    } else {
                        ApiResult.Error("No se pudo convertir el producto creado")
                    }
                } else {
                    ApiResult.Error("No se pudo crear el producto")
                }
            } else {
                ApiResult.Error("Error HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Error al crear producto", e)
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }

    suspend fun updateProducto(
        id: String,
        nombre: String? = null,
        descripcion: String? = null,
        precio: Double? = null,
        stock: Int? = null
    ): ApiResult<Producto> = withContext(Dispatchers.IO) {
        try {
            val request = UpdateProductoRequest(
                nombre = nombre,
                descripcion = descripcion,
                precio = precio,
                stock = stock
            )

            val response = apiService.updateProducto(id, request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val producto = body.data.toDomain()
                    if (producto != null) {
                        ApiResult.Success(producto)
                    } else {
                        ApiResult.Error("No se pudo convertir el producto actualizado")
                    }
                } else {
                    ApiResult.Error("No se pudo actualizar el producto")
                }
            } else {
                ApiResult.Error("Error HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Error al actualizar producto", e)
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }

    suspend fun deleteProducto(id: String): ApiResult<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteProducto(id)

            if (response.isSuccessful) {
                ApiResult.Success(Unit)
            } else {
                ApiResult.Error("Error HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Error al eliminar producto", e)
            ApiResult.Error("Error de red: ${e.message}", e)
        }
    }

    suspend fun uploadImage(id: String, imageFile: File): ApiResult<Producto> = withContext(Dispatchers.IO) {
        try {
            val requestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("file", imageFile.name, requestBody)

            val response = apiService.uploadProductoImage(id, part)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    val producto = body.data.toDomain()
                    if (producto != null) {
                        ApiResult.Success(producto)
                    } else {
                        ApiResult.Error("No se pudo convertir el producto")
                    }
                } else {
                    ApiResult.Error("No se pudo subir la imagen")
                }
            } else {
                ApiResult.Error("Error HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Error al subir imagen", e)
            ApiResult.Error("Error al subir imagen: ${e.message}", e)
        }
    }
}
