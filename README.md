Integración de cámara y galería para Avatar de perfil en android con Jetpack compose

Introducción:

SaborLocalSpa es un proyecto que tiene como principal objetivo el desarrollo de una aplicación android, donde se logra demostrar la capacidad de interactuar con servicios web externos, además de gestionar el flujo de usuario. Desde el inicio de sesión hasta la visualización de datos personales. Para llevar a cabo se creó una aplicación nativa que se conecta a una API REST para así autenticar a los usuarios y mostrar la información del perfil.

Con nuestro informe buscamos documentar el proceso de diseño y desarrollo, enfatizando la implementación de una arquitectura limpia y escalable. Hemos enfatizado además el uso de tecnologías actuales del ecosistema Android tales como kotlin para el lenguaje de programación, Jetpack compose para la construcción de una interfaz de usuario clara y moderna, librería Retrofit para una comunicación eficiente y robusta con el servidor. En resumen se explorará la estructura del proyecto desde las capas de datos hasta la interfaz final del usuario.


2.Arquitectura del sistema:
Para poder garantizar un desarrollo escalable, fácil de mantener y ordenado, nuestra aplicación implementa MVVM siguiendo su patrón de arquitectura. Esta arquitectura divide la aplicación en tres principales capas, cada una con su definida responsabilidad. 

Model(Modelo): representa la capa de datos de la aplicación encargada de obtener y gestionar datos  de la aplicación. Obteniendo y gestionando los datos, ya sea desde una fuente remota como  API REST. Esta capa está compuesta por los modelos de datos clases User, AuthResponse y el UserRepository, que actúa como única fuente para el resto de la aplicación.
View (vista): es la capa de interfaz de usuario o también conocida como UI. Para nuestro proyecto fue construida íntegramente con funciones composable de jetpack, como única responsabilidad debe mostrar en pantalla los datos proporcionados por el ViewModel y capturar las interacciones del usuario, notificando a ViewModel de las acciones.
ViewModel(Vista-Modelo): es un intermediario entre el Modelo y la vista. Encargado de visualizar o exponer los datos del Modelo a la vista a través de estados observables, contiene la lógica de presentación para responder a las interacciones del usuario. Una característica clave es que subsiste a los cambios de configuración del dispositivo, evitando pérdida de estado y el tener que volver a cargar los datos.




Para fortalecer esta arquitectura y promover un bajo acoplamiento, se ha implementado un sistema de Inyección de Dependencias (DI) manual. Se creó una clase contenedora (AppDependencies) que centraliza la creación de dependencias críticas, como el UserRepository. Esta instancia única es "inyectada" en los ViewModels que la necesitan. Este enfoque previene que cada ViewModel cree sus propias dependencias, lo que resulta en un código más eficiente, flexible y mucho más fácil de probar en un futuro.

3.Tecnologías y librerías implementadas:
La selección de tecnologías se ha basado en las herramientas modernas y estándar de la industria para el desarrollo de Android, para así asegurar un óptimo rendimiento y un código de alta calidad con Kotlin, de este modo se aprovechan las características de seguridad, concisión y se saca provecho a las potentes capacidades de programación asíncrona a través de corrutinas.

Interfaz de Usuario
Jetpack Compose: la interfaz de usuario es construida con esta, así se alumina la necesidad de trabajar con archivos XML, permitiendo un rápido desarrollo y una previsualización en tiempo real de los componentes. La navegación entre pantallas se gestiona de forma declarativa a través de Navigation Compose.

Comunicación con la API
 Retrofit y OkHttp Retrofit: con esto podemos definir de manera declarativa los endpoint de API REST en la interfaz de Kotlin. Nos simplifica el proceso de realizar peticiones HTTP y procesar respuestas.
OkHttp & Logging Interceptor: como motor para ejecutar las llamadas de red Retrofit utiliza OkHttp. Así se ha configurado un LoggingIntercepton que imprima en la consola de depuración todas las peticiones salientes y respuestas que entran, es una invaluable herramienta para depurar problemas de conectividad.
Gson Converter: con Retrofit se integran para así convertir automáticamente las respuestas JSON de la API en objetos de datos de Kotlin(data class) y viceversa.

4.Manejo de Asincronía

Coroutines de Kotlin: 
Todas las operaciones de red que son extremadamente largas se ejecutan asincrónicamente utilizando corrutinas. Con esto se puede garantizar  que la idea o estructura  principal de la aplicación no se bloquee, manteniendo la interfaz de usuario fluida y responsiva. 
Gestión de Dependencias: Gradle Version Catalog (libs.versions.toml):
Para una gestión centralizada y limpia de las librerías externas, se utiliza un catálogo de versiones de Gradle. Esto permite definir todas las versiones de las dependencias en un único archivo, evitando conflictos y facilitando futuras actualizaciones.
 


5.Flujo de Autenticación

Vista (LoginScreen): usuario ingresa sus credenciales y pulsa el botón de inicio de sesión, con esta acción se invoca una función en el LoginViewModel.
ViewModel (LoginViewModel): función invocada llama al UserRepository para que gestione la autenticación, traspasando las credenciales.
Repositorio (UserRepository): el repositorio, a través de la instancia de Retrofit, ejecuta la llamada de red al endpoint /auth/login.
Respuesta: cuando la API responde, el resultado (éxito o error) viaja de vuelta por el mismo camino: Repositorio -> ViewModel -> Vista.
Navegación: cuando la autenticación es exitosa, el LoginViewModel utiliza el NavController para ordenar la transición a la pantalla de perfil (ProfileScreen).

6.Conclusión
El proyecto SaborLocalSpa ha cumplido exitosamente sus objetivos, entregando una aplicación Android funcional que gestiona de manera robusta la autenticación de usuarios y la visualización de perfiles mediante la interacción con una API REST.La implementación de la arquitectura MVVM, fortalecida por un sistema de Inyección de Dependencias manual, ha sido clave para lograr una base de código desacoplada, eficiente y altamente mantenible. El uso de un stack tecnológico moderno, que incluye Jetpack Compose, Retrofit y Coroutines, no solo garantiza una experiencia de usuario fluida, sino que también alinea el proyecto con los estándares actuales de la industria del desarrollo de software.
En definitiva, SaborLocalSpa no solo es una solución funcional a los requisitos planteados, sino también una demostración práctica de cómo aplicar principios de diseño avanzados para construir aplicaciones de alta calidad.
