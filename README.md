##Proyecto Semestral:  SaborLocalSpa
SaborLocal es una aplicación móvil Android que conecta productores locales de alimentos con clientes, permitiendo explorar productos artesanales, ver sus detalles, agregarlos al carrito y gestionar pedidos en línea a través de una API REST desplegada en la nube con autenticación JWT y manejo de roles (CLIENTE, PRODUCTOR, ADMIN).

## Integrantes

- Fernanda Collinao – Frontend Android / Integración API  
- Alexiss Pérez – Frontend Android / Integración API y coordinación con Backend  

## Repositorios

- Frontend (App Android): https://github.com/alexissperez/SaborLocalSpa.git
- Backend (API SaborLocal): https://github.com/alexissperez/Saborlocal.api.git

## Descripción general

La app permite registrarse como cliente o productor, iniciar sesión, navegar por productos y productores, agregar productos al carrito y crear pedidos consumiendo datos desde un backend propio desplegado en Render y conectado a MongoDB Atlas.
Las imágenes de productos se almacenan en el servidor y se sirven mediante URLs que la app consume con Coil, construidas a partir de las rutas `imagen` e `imagenThumbnail` guardadas en la base de datos.

## Tecnologías y herramientas

- Frontend móvil: Kotlin, Android, Jetpack Compose, ViewModel, StateFlow.
- Red y datos: Retrofit, OkHttp, Gson, interceptores para token JWT.
- Carga de imágenes: Coil, usando URLs construidas desde las rutas de imagen devueltas por la API.
- Backend: API REST en NestJS/Node desplegada en Render, con módulos para auth, usuarios, productos, productores y pedidos.
- Base de datos: MongoDB Atlas como base de datos en la nube para usuarios, productores, productos, pedidos y entregas.
- Pruebas de API: Postman, usado para probar autenticación, CRUD de recursos, subida de imágenes y generar datos de prueba.

## Estructura de carpetas (Android)

```text
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
        │   │   ├── AuthSaborLocalRepository.kt
        │   │   └── ProductoRepository.kt
        │   ├── ui/
        │   │   ├── components/
        │   │   │   ├── ShadcnCard.kt
        │   │   │   ├── HomeProductoCard.kt
        │   │   │   └── ProductorCard.kt
        │   │   └── screens/
        │   │       ├── HomeScreen.kt
        │   │       ├── RegisterScreen.kt
        │   │       ├── LoginScreen.kt
        │   │       ├── CreateProductoScreen.kt
        │   │       └── ProductosListScreen.kt
        │   └── viewmodel/
        │       ├── HomeViewModel.kt
        │       ├── RegisterViewModel.kt
        │       ├── LoginViewModel.kt
        │       ├── ProductoViewModel.kt
        │       └── CreateProductoViewModel.kt
        └── res/...
```

`data` centraliza el acceso remoto/local y los mapeos DTO → dominio, `model` define los modelos usados por la UI, `repository` encapsula la lógica de comunicación con la API, `ui` contiene pantallas y componentes reutilizables y `viewmodel` maneja el estado y la lógica de presentación.[3]

## Flujo de datos, Postman y MongoDB Atlas

La app Android solo se comunica con la API REST mediante Retrofit (base URL `https://saborlocal-api.onrender.com/api/`) y nunca accede directamente a MongoDB Atlas, lo que asegura un bajo acoplamiento entre cliente y base de datos.
Durante el desarrollo se utilizó Postman para registrar usuarios de prueba, crear y actualizar productos y productores, y probar la subida de imágenes con multipart/form-data, verificando que las rutas se guardaran correctamente en MongoDB Atlas antes de integrarlas en la app.

## Integración con MongoDB Atlas

La API se conecta a MongoDB Atlas usando una cadena de conexión configurada en la variable de entorno `MONGODB_URI`.
Se manejan colecciones para usuarios (con roles y contraseñas encriptadas), productores (perfil, ubicación, categorías), productos (referenciando al productor por su `_id`) y pedidos/entregas, incluyendo campos de rutas de imagen que la app usa para construir las URLs finales.

## Funcionalidades principales

- Registro e inicio de sesión con JWT y manejo de roles CLIENTE, PRODUCTOR y ADMIN.
- Home con productos recientes, productores destacados y accesos rápidos a secciones como carrito y pedidos.
- Listado y detalle de productos, mostrando imagen, precio, unidad, stock, descripción y productor, con acción para agregar al carrito.
- Gestión de carrito y pedidos: agregar/quitar ítems, cálculo de subtotal y total y creación de pedidos asociados al cliente autenticado.
- Panel para productores/admin con formulario para crear productos, validaciones en el ViewModel y gestión (listado/eliminación) de productos propios.

## Endpoints usados

Base URL:

```text
https://saborlocal-api.onrender.com/api/
```

Principales endpoints de la API SaborLocal:

- Autenticación  
  - `POST /auth/register` – Registro de usuario.  
  - `POST /auth/login` – Inicio de sesión y obtención de JWT.  
  - `GET /auth/profile` – Perfil del usuario autenticado.  

- Productores  
  - `GET /productores` – Listar productores.  
  - `GET /productores/{id}` – Obtener productor por id.  
  - `POST /productores/profile` – Crear perfil de productor.  

- Productos  
  - `GET /productos` – Listar productos.  
  - `GET /productos/{id}` – Detalle de producto.  
  - `GET /productos/productor/{productorId}` – Productos por productor.  
  - `POST /productos` – Crear producto.  
  - `PATCH /productos/{id}` – Actualizar producto.  
  - `DELETE /productos/{id}` – Eliminar producto.  
  - `POST /productos/{id}/upload-image` – Subir imagen de producto.  

- Clientes y pedidos  
  - `GET /clientes/{id}` – Datos de cliente.  
  - `POST /pedidos` – Crear pedido.  
  - `GET /pedidos/cliente/{clienteId}` – Pedidos por cliente.  
  - `GET /pedidos/{id}` – Detalle de pedido.  

No se consumen APIs externas; toda la información proviene del backend propio y de MongoDB Atlas.

## Usuarios de prueba y roles

Para la corrección se definieron usuarios de prueba para cada rol del sistema:

- CLIENTE: `cliente@saborlocal.cl` / `Cliente123`  
- PRODUCTOR: `productor@saborlocal.cl` / `Productor123`  
- ADMIN: `admin@saborlocal.cl` / `Admin123`  

Estos usuarios se crearon inicialmente mediante Postman utilizando el endpoint `/auth/register`.

## Instrucciones para ejecutar el proyecto

### Backend (API SaborLocal)

1. Clonar el repositorio: `git clone https://github.com/alexissperez/Saborlocal.api.git`.
2. Configurar variables de entorno (por ejemplo en `.env`):  
   - `MONGODB_URI` – cadena de conexión a MongoDB Atlas.
   - `JWT_SECRET` – clave para firmar tokens JWT.
   - `PORT` – puerto del servidor.
3. Instalar dependencias y levantar el servidor:

```bash
npm install
npm run start:prod    # o npm run start:dev
```

4. Verificar el endpoint de salud, por ejemplo: `https://saborlocal-api.onrender.com/api/health`.

### App móvil Android

1. Clonar el repositorio: `git clone https://github.com/alexissperez/SaborLocalSpa.git`.
2. Abrir el proyecto en Android Studio.
3. Confirmar la URL base de Retrofit en `RetrofitClient.kt`:

```kotlin
const val BASE_URL = "https://saborlocal-api.onrender.com/api/"
```

4. Conectar un emulador o dispositivo físico y ejecutar la app desde Android Studio.

## APK firmado y archivo .jks

El APK firmado de release se ubica en `app/release/SaborLocal-v1.0-signed.apk`.
El archivo de keystore (`saborlocal-release.jks`) no se versiona en el repositorio público y se configura en la sección `signingConfigs` del módulo `app` en `build.gradle`.

## Evidencia de trabajo colaborativo

El control de versiones se realizó con Git en GitHub, con commits distribuidos en ambos repositorios entre Fernanda Collinao y Alexiss Pérez, lo que permite revisar el historial de cambios, ramas y contribuciones de cada integrante.
