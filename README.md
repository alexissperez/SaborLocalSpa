SaborLocal
SaborLocal es una aplicación móvil Android que conecta productores locales de alimentos con clientes, permitiendo explorar productos artesanales, ver sus detalles, agregarlos al carrito y gestionar pedidos en línea.​
La app consume una API REST desplegada en la nube, con autenticación basada en JWT y manejo de roles (CLIENTE, PRODUCTOR, ADMIN).​

Integrantes
Fernanda Collinao 1 – Rol principal (Frontend Android)

Alexiss Pérez 2 – Rol principal (Base de datos / QA / API)

Tecnologías y herramientas
Frontend móvil: Kotlin, Android, Jetpack Compose, ViewModel, StateFlow.​

Red y datos: Retrofit, OkHttp, Gson, manejo de interceptores para token JWT.​

Carga de imágenes: Coil, construyendo URLs a partir de rutas almacenadas en la API.​

Backend: API REST (NestJS/Node) desplegada en Render, siguiendo arquitectura por módulos.​

Base de datos: MongoDB Atlas como base de datos en la nube para productos, productores, usuarios, pedidos y entregas.​

Pruebas de API y soporte al desarrollo: Postman para probar endpoints, crear datos de prueba y verificar estructura de respuestas JSON antes de integrarlas en la app móvil.​

Funcionalidades principales
Registro e inicio de sesión con autenticación JWT y manejo de roles (CLIENTE, PRODUCTOR, ADMIN).​

Home con:
Productos recientes con imagen, nombre, precio y productor.​
Productores destacados.​
Accesos rápidos a productores, productos, carrito y pedidos.​

Listado y detalle de productos:
Visualización de imágenes servidas por la API (rutas imagen e imagenThumbnail guardadas en MongoDB Atlas y consumidas vía Coil).​
Información de productor, precio, unidad, stock y descripción, con aviso de stock bajo.​
Agregar productos al carrito desde el detalle.​

Gestión de carrito y pedidos:
Agregar y quitar productos del carrito.​
Cálculo de subtotal y total del pedido.​
Creación de pedidos con listado de ítems asociados al cliente.​

Panel para productor / admin:
Crear nuevos productos desde la app mediante formulario validado en el ViewModel.​
Ver productos propios y eliminarlos según permisos del rol.​

Integración con subida de imágenes:
Subida de imágenes hacia la API (probadas con Postman usando multipart/form-data).​
Almacenamiento de rutas imagen e imagenThumbnail en MongoDB Atlas, consumidas luego por la app.​

Descripción de arquitectura y flujo de datos
La app Android se comunica exclusivamente con la API REST mediante Retrofit, enviando y recibiendo JSON, sin conectarse directamente a la base de datos.​
La API se encarga de gestionar la lógica de negocio, seguridad con JWT y acceso a MongoDB Atlas usando una cadena de conexión configurada como variable de entorno.​

Durante el desarrollo se utilizó Postman para:​

Registrar usuarios con distintos roles y verificar el flujo de login.
Crear, actualizar y eliminar productos y productores verificando el formato de los JSON.
Probar subida de archivos (multipart/form-data) para imágenes de productos y validar que las rutas se guarden correctamente en MongoDB Atlas.
Una vez validados los endpoints en Postman, se replicaron los mismos cuerpos y rutas en los DTO (CreateProductoRequest, UpdateProductoRequest) y en los métodos del repositorio (ProductoRepository.createProducto, updateProducto, uploadImage).​

Estructura de carpetas (Android)
text
app/
└── src/
    └── main/
        ├── java/com/example/saborlocalspa/
        │   ├── data/
        │   │   ├── local/
        │   │   │   └── TokenManager.kt
        │   │   ├── mapper/
        │   │   │   ├── ProductoMapper.kt
        │   │   │   └── PedidoMapper.kt
        │   │   └── remote/
        │   │       ├── api/
        │   │       │   ├── SaborLocalAuthApiService.kt
        │   │       │   ├── SaborLocalProductoApiService.kt
        │   │       │   ├── SaborLocalProductorApiService.kt
        │   │       │   ├── SaborLocalClienteApiService.kt
        │   │       │   └── SaborLocalPedidoApiService.kt
        │   │       ├── dto/
        │   │       │   ├── auth/
        │   │       │   ├── producto/
        │   │       │   │   ├── CreateProductoRequest.kt
        │   │       │   │   └── UpdateProductoRequest.kt
        │   │       │   └── pedido/
        │   │       │       └── ClienteDto.kt
        │   │       └── RetrofitClient.kt
        │   ├── model/
        │   │   ├── Producto.kt
        │   │   ├── Productor.kt
        │   │   ├── Pedido.kt
        │   │   ├── Cliente.kt
        │   │   └── ApiResult.kt
        │   ├── repository/
        │   │   └── ProductoRepository.kt
        │   ├── ui/
        │   │   ├── components/
        │   │   │   ├── ShadcnCard.kt
        │   │   │   ├── HomeProductoCard.kt
        │   │   │   └── ProductorCard.kt
        │   │   └── screens/
        │   │       ├── HomeScreen.kt
        │   │       ├── CreateProductoScreen.kt
        │   │       └── ProductosListScreen.kt
        │   └── viewmodel/
        │       ├── HomeViewModel.kt
        │       ├── ProductoViewModel.kt
        │       └── CreateProductoViewModel.kt
        └── res/...
data/: acceso a datos remotos y locales, más mapeo DTO → modelo de dominio.​

model/: modelos de dominio usados por UI y repositorios.​

repository/: encapsula la lógica de comunicación con la API.​

ui/: pantallas (screens) y componentes reutilizables (components).​

viewmodel/: lógica de presentación, manejo de estado y orquestación de repositorios.​

Endpoints usados
Propios (API SaborLocal)
Base URL:

text
https://saborlocal-api.onrender.com/api/
Principales endpoints (método + ruta):​

Autenticación

POST /auth/register – Registro de usuario.
POST /auth/login – Inicio de sesión y obtención de JWT.
GET /auth/profile – Perfil del usuario autenticado.

Productores

GET /productores – Listar productores.
GET /productores/{id} – Obtener productor por id.
POST /productores/profile – Crear perfil de productor para un usuario PRODUCTOR.

Productos

GET /productos – Listar productos.
GET /productos/{id} – Obtener detalle de producto.
GET /productos/productor/{productorId} – Listar productos por productor.
POST /productos – Crear producto.
PATCH /productos/{id} – Actualizar producto.
DELETE /productos/{id} – Eliminar producto.
POST /productos/{id}/upload-image – Subir imagen asociada al producto.

Clientes y pedidos

GET /clientes/{id} – Obtener información de cliente.
POST /pedidos – Crear pedido.
GET /pedidos/cliente/{clienteId} – Listar pedidos por cliente.
GET /pedidos/{id} – Obtener detalle de pedido.

Endpoints externos
No se integran APIs públicas externas; toda la información proviene del backend propio desplegado en Render y de MongoDB Atlas.​

Instrucciones para ejecutar el proyecto
1. Backend (microservicios / API)
Clonar el repositorio del backend.​

Configurar variables de entorno (por ejemplo en .env):

MONGODB_URI: cadena de conexión a MongoDB Atlas.​

JWT_SECRET: clave secreta para firmar tokens JWT.​

PORT: puerto de ejecución del servidor.​

Instalar dependencias y levantar el servidor (NestJS/Node):

bash
npm install
npm run start:prod   # o start:dev según configuración
Verificar que la API responde en un endpoint de salud, por ejemplo:​

text
https://saborlocal-api.onrender.com/api/health
2. App móvil Android
Clonar el repositorio de la app móvil.​

Abrir el proyecto en Android Studio.​

Verificar la URL base de la API en RetrofitClient.kt:​

kotlin
const val BASE_URL = "https://saborlocal-api.onrender.com/api/"
Conectar un dispositivo físico o emulador.​

Compilar y ejecutar la app desde Android Studio (Run ▶).​

3. Uso de Postman durante el desarrollo
Probar todos los endpoints de autenticación, productos y productores.​

Crear datos iniciales de prueba (usuarios, productores, productos).​

Subir imágenes y obtener las rutas que se almacenan en MongoDB Atlas.​

Verificar códigos de respuesta y estructura JSON antes de integrar con la app móvil.​

APK firmado y archivo .jks
APK firmado de release:

Ruta sugerida en el repositorio: app/release/SaborLocal-v1.0-signed.apk.​

Archivo de keystore (.jks):

No se versiona en el repositorio público por seguridad.​

Ruta local usada en el proyecto (documentar para corrección): keystore/saborlocal-release.jks.​

Configuración referenciada en app/build.gradle en la sección signingConfigs.​

Código fuente
Microservicios / API:

Repositorio: <(https://github.com/alexissperez/Saborlocal.api.git)>

Estructura por módulos (auth, productos, productores, pedidos), conexión a MongoDB Atlas y controladores REST.​

Aplicación móvil Android:

Repositorio: <(https://github.com/alexissperez/SaborLocalSpa.git)>

Estructura principal (carpetas data, model, repository, ui, viewmodel tal como se describe arriba).​

Evidencia de trabajo colaborativo
El repositorio usa Git como control de versiones, con commits distribuidos entre los integrantes.​​

Ejemplos de actividades por persona:

Integrante 1: implementación de Home, tarjetas de producto, integración con Retrofit y Coil.​
Integrante 2: endpoints de productos y productores, subida de imágenes y conexión a MongoDB Atlas.​
Integrante 3: flujo de pedidos y carrito, documentación y pruebas con Postman.
