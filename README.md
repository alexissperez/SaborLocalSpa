Integración de cámara y galería para Avatar de perfil en android con Jetpack compose

Caso: SaborLocalSpa

SaborLocalSpa es un proyecto que tiene como principal objetivo el desarrollo de una aplicación android, donde se logra demostrar la capacidad de interactuar con servicios web externos, además de gestionar el flujo de usuario. Desde el inicio de sesión hasta la visualización de datos personales. Para llevar a cabo se creó una aplicación nativa que se conecta a una API REST para así autenticar a los usuarios y mostrar la información del perfil.

Con nuestro informe buscamos documentar el proceso de diseño y desarrollo, enfatizando la implementación de una arquitectura limpia y escalable. Hemos enfatizado en Diseño/UI con Jetpack Compose, validaciones en formularios de autenticación, navegación entre pantallas, gestión de estado con ViewModel, persistencia básica de datos, y uso de recursos nativos del dispositivo (cámara y galería) para la selección y carga del avatar de perfil.
2. Requisitos y ejecución
Stack:


Lenguaje: Kotlin


Framework/UI: Jetpack Compose


Arquitectura: MVVM (Model–View–ViewModel)


Comunicación con API: Retrofit + OkHttp + Gson Converter


Asincronía: Kotlin Coroutines


Gestión de dependencias: Gradle con Version Catalog


Inyección de dependencias: Implementación manual mediante clase AppDependencies


Instalación:
Clonar el repositorio:

 git clone 
cd saborlocalspa

Abrir el proyecto en Android Studio Hedgehog o superior.
Sincronizar dependencias con Gradle.

Ejecución:
Seleccionar un emulador Android 12+ o dispositivo físico.
Ejecutar desde Android Studio con el perfil Debug.


3. Arquitectura y flujo
Para poder garantizar un desarrollo escalable, fácil de mantener y ordenado, nuestra aplicación implementa MVVM siguiendo su patrón de arquitectura. Esta arquitectura divide la aplicación en tres principales capas, cada una con su definida responsabilidad. 
Estructura de carpetas:

app/
├── java/
│ └── com.example.saborlocalspa/
│ ├── data/
│ │ ├── local/ dao/database/entity
│ │ ├── remote/ dto/ ApiService /AppNavigation/ Authinterceptor/ RetrofitClient
│ │ └── repository/ AvatarRepository.kt
│ │
│ ├── ui/
│ │ ├── components/ 
│ │ ├── navigation/
│ │ ├── screens/ 
│ │ └── theme/
│ │
│ ├── utils/
│ ├── viewmodel/ 
│ │
│ ├── AppDependencies.kt 
│ └── MainActivity.kt 
│
├── androidTest/ 
│ └── ExampleInstrumentedTest.kt
│
└── test/ Pruebas unitarias
└── ExampleUnitTest.kt
data/local: clases para persistencia local (DataStore).
data/remote: conexión a la API externa, clientes de red (Retrofit, endpoints).
repository: contiene los repositorios que median entre ViewModel y fuentes de datos (UserRepository, AvatarRepository).
ui/components: elementos reutilizables de interfaz (botones, inputs, avatar picker).
ui/navigation: definición de rutas y control del flujo con NavHost.
ui/screens:pantallas principales (LoginScreen, ProfileScreen, ForgotPasswordScreen).
ui/theme: paleta de colores, tipografía y estilos de Compose.
utils	Funciones de validación o utilidades generales (ValidationUtils.kt).
viewmodel	Lógica de negocio y manejo de estado de cada pantalla (LoginViewModel, ProfileViewModel ).
AppDependencies.kt	Inyección de dependencias manual (repositorios, ViewModels).
MainActivity.kt	Punto de entrada de la app, donde se inicializa la navegación y el contenido Compose.
Gestión de estado:
Se utiliza ViewModel para mantener el estado entre cambios de configuración.
La vista observa estados inmutables (loading, success, error) mediante StateFlow.
El estado local (por ejemplo, selección temporal de imagen) se maneja con remember y mutableStateOf.
Navegación:
Implementada con Navigation Compose, utilizando un stack declarativo.


Flujo: LoginScreen → ProfileScreen.
La navegación se gestiona desde el NavController según el estado de autenticación.
Para fortalecer esta arquitectura y promover un bajo acoplamiento, se ha implementado un sistema de Inyección de Dependencias (DI) manual. Se creó una clase contenedora (AppDependencies) que centraliza la creación de dependencias críticas, como el UserRepository. Esta instancia única es "inyectada" en los ViewModels que la necesitan. Este enfoque previene que cada ViewModel cree sus propias dependencias, lo que resulta en un código más eficiente, flexible y mucho más fácil de probar en un futuro.
4. Funcionalidades
Formulario validado:
 Pantalla de login con campos de correo y contraseña. Validaciones de formato y no vacíos antes del envío.

Navegación y backstack:
Flujo controlado por NavController, preservando el historial de pantallas y evitando volver al login tras iniciar sesión correctamente.


Gestión de estado:
Estado de carga mientras se espera la respuesta del servidor.
Estado de error con mensajes informativos.
Estado de éxito que redirige al perfil del usuario.

Persistencia local y almacenamiento de imagen de perfil:
El token de autenticación y algunos datos del usuario se almacenan localmente mediante DataStore.
La imagen de perfil se obtiene desde la galería o cámara y se almacena en caché local para persistir entre sesiones.

Recursos nativos (cámara y galería):
Integración mediante ActivityResultContracts para capturar o seleccionar imágenes.
Manejo explícito de permisos de cámara y almacenamiento, con fallback a galería si el permiso no es concedido.

Animaciones:
Se utilizan animaciones sutiles de entrada/salida de componentes en Compose para mejorar la experiencia visual (por ejemplo, al cargar el avatar).

Consumo de API:
Uso de Retrofit para consumir los endpoints /auth/login y /auth/me.
Manejo de errores HTTP y conversión automática de JSON mediante Gson Converter.



5. Endpoints
Base URL:
Método
Ruta
Body
Respuesta
POST
/auth/signup
{ email, password, name }
201 { authToken, user: { id, email } }
POST
/auth/login
{ email, password }
200 { authToken, user: { id, email } }
GET
/auth/me
(requiere header Authorization)
200 { id, email, name, avatarUrl }


6. User flows
Flujo principal (autenticación y perfil):
LoginScreen: el usuario ingresa credenciales y pulsa “Iniciar sesión”.
LoginViewModel: procesa el evento, ejecuta la llamada UserRepository.loginUser().
UserRepository: realiza la solicitud POST /auth/login vía Retrofit.

Respuesta:
Éxito: se almacena el token y se navega a ProfileScreen.
Error: se muestra mensaje informativo.
ProfileScreen: muestra los datos del usuario obtenidos desde /auth/me y permite actualizar el avatar mediante cámara o galería.


Casos de error manejados:
Credenciales incorrectas (mensaje visible en UI).
Fallos de conexión (reintento manual).
Permiso de cámara denegado (fallback automático a galería).

La selección de tecnologías se ha basado en las herramientas modernas y estándar de la industria para el desarrollo de Android, para así asegurar un óptimo rendimiento y un código de alta calidad con Kotlin, de este modo se aprovechan las características de seguridad, concisión y se saca provecho a las potentes capacidades de programación asíncrona a través de corrutinas.
6.Conclusión
El proyecto SaborLocalSpa ha cumplido exitosamente sus objetivos, entregando una aplicación Android funcional que gestiona de manera robusta la autenticación de usuarios y la visualización de perfiles mediante la interacción con una API REST.La implementación de la arquitectura MVVM, fortalecida por un sistema de Inyección de Dependencias manual, ha sido clave para lograr una base de código desacoplada, eficiente y altamente mantenible. El uso de un stack tecnológico moderno, que incluye Jetpack Compose, Retrofit y Coroutines, no solo garantiza una experiencia de usuario fluida, sino que también alinea el proyecto con los estándares actuales de la industria del desarrollo de software.
En definitiva, SaborLocalSpa no solo es una solución funcional a los requisitos planteados, sino también una demostración práctica de cómo aplicar principios de diseño avanzados para construir aplicaciones de alta calidad.
