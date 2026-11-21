# Creustacico

Creustacico es una aplicación desarrollada en Java como trabajo práctico final de la materia Tecnologías Java.

El proyecto implementa:

- Un servidor TCP multicliente basado en sockets.
- Un cliente de consola para enviar solicitudes por texto.
- Un framework propio para registrar recursos, acciones y servicios.
- Sesiones independientes para cada conexión.
- Usuarios con roles `USUARIO` y `ADMIN`.
- Permisos declarados por cada controlador.
- Un módulo inicial de notas en desarrollo.

## Integrantes

- Darío Salvetti - DNI 41427479
- Matías Beratz - DNI 44933215
- Ivana Schwindt - DNI 26704897

## Objetivos de aprendizaje

- Programación orientada a objetos.
- Registros (`record`) y enums.
- Comunicación cliente-servidor mediante sockets TCP.
- Concurrencia con un hilo por cliente.
- Protocolo de solicitudes y respuestas basado en texto.
- Registro y despacho dinámico de acciones.
- Inyección básica de servicios mediante un contenedor propio.
- Gestión de sesiones por conexión.
- Manejo de roles y permisos.
- Separación entre framework reutilizable y aplicación concreta.
- Manejo de excepciones controladas y errores internos.

## Requisitos

- Java JDK 17 o superior.
- Visual Studio Code con soporte para Java, opcional.

Para comprobar la instalación:

```sh
java -version
javac -version
```

## Compilación y ejecución

Abrir una terminal dentro de la carpeta `tpfinal` y compilar el proyecto:

```sh
javac -d bin src/framework/*.java src/app/main/*.java src/app/models/*.java src/cliente/*.java
```

Desde el mismo directorio, iniciar el servidor:

```sh
java -cp bin app.main.Main
```

El servidor escucha en el puerto `3000` y queda a la espera de conexiones. Para iniciar el cliente de consola en otra terminal:

```sh
java -cp bin cliente.ClienteConsola
```

También se puede indicar un host y un puerto:

```sh
java -cp bin cliente.ClienteConsola localhost 3000
```

Para desconectarse desde el cliente se puede escribir `salir`.

## Uso rápido

Al establecer la conexión, el servidor responde:

```text
Bienvenido
```

Las solicitudes tienen el siguiente formato:

```text
recurso/accion/parametro1/parametro2
```

- Cada línea enviada por el cliente representa una solicitud independiente.
- Los parámetros están separados por `/`.
- Las respuestas exitosas comienzan con `OK `.
- Los errores controlados comienzan con `ERROR `.
- Los recursos y acciones inexistentes pueden producir un error interno, y el servidor registra el detalle en la consola.

### Usuarios disponibles

| Usuario | Clave | Rol |
|---|---|---|
| `ana` | `1234` | `USUARIO` |
| `beto` | `4321` | `USUARIO` |
| `admin` | `admin` | `ADMIN` |

### Iniciar sesión

```text
usuarios/login/ana/1234
```

Respuesta:

```text
OK Hola, ana (USUARIO)
```

La sesión pertenece a la conexión. Si se inicia sesión desde un cliente, otro cliente conectado no comparte esa sesión.

### Consultar el usuario actual

```text
usuarios/usuarioLogueado
```

Con una sesión activa:

```text
OK Usuario logueado: ana (USUARIO)
```

Sin una sesión activa:

```text
OK (nadie)
```

### Cerrar sesión

```text
usuarios/logout
```

La sesión se limpia y posteriormente `usuarioLogueado` informa `(nadie)`.

## Arquitectura general

```text
ClienteConsola
      |
      | socket TCP / texto
      v
Servidor
  |-- acepta una conexión
  |-- crea una Sesion para el cliente
  |-- crea un hilo de atención
  |-- llama a ParsearSolicitud
  |-- delega a Router
  |-- ejecuta la acción del Controlador
  `-- devuelve una respuesta
```

Cada conexión se procesa en un hilo independiente. El servidor crea una instancia de `Sesion` dentro de `atender`, de modo que el usuario y el rol están aislados por cliente.

## Framework implementado

### `Servidor`

Archivo: `src/framework/Servidor.java`

- Abre un `ServerSocket` sobre el puerto indicado.
- Acepta conexiones de clientes en forma continua.
- Crea un hilo nuevo para cada cliente.
- Mantiene una sesión independiente por conexión.
- Envía `Bienvenido` al conectarse.
- Atiende solicitudes línea por línea.
- Ignora la solicitud `salir` como instrucción de desconexión.
- Contiene el método `procesar`, que diferencia excepciones controladas de errores inesperados.
- Expone el registro de servicios mediante `getServicios()`.

### `Comunicador`

Archivo: `src/framework/Comunicador.java`

- Encapsula los flujos de entrada y salida del socket.
- Lee y envía líneas de texto.
- Implementa `AutoCloseable` para cerrar los recursos con `try-with-resources`.

### `ParsearSolicitud`

Archivo: `src/framework/ParsearSolicitud.java`

- Verifica que la línea no esté vacía.
- Exige al menos recurso y acción.
- Divide el texto con `/`.
- Convierte el texto en un objeto `Solicitud`.
- Los elementos posteriores a recurso y acción se consideran parámetros.

Ejemplo:

```text
usuarios/login/ana/1234
```

Produce conceptualmente:

```text
recurso: usuarios
accion: login
parametros: [ana, 1234]
```

### `Solicitud`

Archivo: `src/framework/Solicitud.java`

Representa una solicitud con:

- Recurso.
- Acción.
- Lista de parámetros.
- Sesión asociada.

El método `getParametro(indice, nombre)` obtiene un parámetro por posición y lanza `SolicitudInvalidaException` si falta.

### `Router`

Archivo: `src/framework/Router.java`

- Guarda los controladores en un `Map` usando el nombre del recurso como clave.
- Busca el recurso y la acción solicitados.
- Ejecuta la función asociada a la acción.

### `Controlador`

Archivo: `src/framework/Controlador.java`

Define la interfaz común que deben implementar los controladores:

- `recurso()`: nombre del recurso.
- `acciones()`: acciones disponibles y funciones que las procesan.
- `permisos()`: permisos opcionales por acción.

### `Servicios`

Archivo: `src/framework/Servicios.java`

Permite registrar y obtener servicios mediante su clase Java. Por ejemplo, `ServicioUsuarios` se registra con `ServicioUsuarios.class` y luego puede ser obtenido por `ControladorUsuarios`.

Si se solicita un servicio que no fue registrado, se lanza una excepción controlada del framework.

### `Sesion`

Archivo: `src/framework/Sesion.java`

Guarda el usuario y el rol asociados a una conexión. Permite iniciar la sesión, cerrarla y comprobar si existe un usuario conectado. Además, el método `verificar(Rol requerido)` permite comprobar autenticación y permisos.

`ADMIN` puede realizar acciones de `USUARIO` y `ADMIN`. `USUARIO` sólo puede realizar acciones que requieran `USUARIO`.

### `Rol`

Archivo: `src/framework/Rol.java`

Enum utilizado para representar los roles:

- `USUARIO`
- `ADMIN`

Su método `puedeHacer(Rol requerido)` determina si un rol tiene acceso a una acción.

### Excepciones

Archivos: `src/framework/ExcepcionesFramework.java` y `src/framework/ExcepcionesChecked.java`

- `ExcepcionesFramework`: error general del framework o de la aplicación.
- `SolicitudInvalidaException`: falta o es incorrecto un parámetro.
- `AccesoDenegadoException`: el usuario no inició sesión o no tiene permisos suficientes.
- `ExcepcionesChecked`: error de entrada/salida al iniciar o mantener el servidor, por ejemplo, si el puerto no está disponible.

`ExcepcionesChecked` hereda de `Exception` para que el código que inicia el servidor deba contemplar una falla previsible del entorno o de la configuración. `Servidor.iniciar()` la lanza cuando ocurre un error de socket y conserva la causa original; `Main` la captura y comunica el problema.

`ExcepcionesFramework` y sus subclases, incluida `SolicitudInvalidaException`, heredan de `RuntimeException`. Representan problemas encontrados al procesar una solicitud en tiempo de ejecución, como datos faltantes o inválidos y permisos insuficientes. No obligan a declarar o capturar cada error en todas las capas: `Servidor.procesar()` los convierte en respuestas controladas `ERROR <mensaje>`. Los errores inesperados se registran en el servidor y se responden al cliente con `Error interno del servidor`.

## Aplicación

### `Main`

Archivo: `src/app/main/Main.java`

Crea el servidor en el puerto `3000`, registra `ServicioUsuarios` y registra `ControladorUsuarios`. Luego inicia el servidor y captura posibles errores de inicio.

Actualmente `ControladorNotas` no está registrado en `Main`.

### `Usuario`

Archivo: `src/app/main/Usuario.java`

Es un `record` con nombre, clave y rol del usuario.

### `ServicioUsuarios`

Archivo: `src/app/main/ServicioUsuarios.java`

Mantiene una colección de usuarios en memoria:

- `ana` / `1234` / `USUARIO`
- `beto` / `4321` / `USUARIO`
- `admin` / `admin` / `ADMIN`

El método `autenticar` verifica las credenciales y devuelve el usuario correspondiente. Los datos no se guardan en una base de datos o archivo.

### `ControladorUsuarios`

Archivo: `src/app/main/ControladorUsuarios.java`

Recurso: `usuarios`

| Acción | Parámetros | Resultado |
|---|---|---|
| `login` | nombre, clave | Inicia la sesión del usuario. |
| `logout` | ninguno | Cierra la sesión actual. |
| `usuarioLogueado` | ninguno | Muestra el usuario activo o `(nadie)`. |

Ejemplos:

```text
usuarios/login/beto/4321
usuarios/usuarioLogueado
usuarios/logout
```

### `ClienteConsola`

Archivo: `src/cliente/ClienteConsola.java`

Se conecta mediante TCP a un host y puerto, lee las respuestas del servidor en un hilo separado y permite escribir solicitudes desde la consola. Por defecto utiliza `localhost:3000` y utiliza UTF-8.

## Módulo de notas

### `Notas`

Archivo: `src/app/models/Notas.java`

Es un `record` con los datos `nombre`, `nota`, `nota2` y `nota3`.

### `ControladorNotas`

Archivo: `src/app/main/ControladorNotas.java`

Recurso: `notas`

| Acción | Rol requerido |
|---|---|
| `verNota` | `USUARIO` |
| `cargarNota` | `ADMIN` |
| `modificarNota` | `ADMIN` |

Solicitudes previstas:

```text
notas/verNota
notas/cargarNota
notas/modificarNota
```

Las acciones todavía son un esqueleto y devuelven el texto `notas`. Falta registrar el controlador en `Main`, implementar el manejo real de notas y aplicar los permisos desde el flujo de despacho.

## Estructura del proyecto

```text
tpfinal/
|-- src/
|   |-- framework/       Framework del servidor
|   |-- app/
|   |   |-- main/        Usuario y controladores
|   |   `-- models/      Modelo de notas
|   `-- cliente/         Cliente de consola
|-- bin/                 Archivos compilados
|-- lib/                 Librerías externas
`-- README.md
```

## Flujo de una solicitud

1. El cliente envía una línea al servidor.
2. `Comunicador` recibe el texto.
3. `ParsearSolicitud` crea una `Solicitud` con recurso, acción y parámetros.
4. La sesión del cliente se agrega a la solicitud.
5. `Router` busca el recurso y la acción correspondiente.
6. El controlador ejecuta la acción y devuelve un resultado.
7. El servidor responde con `OK <resultado>` o `ERROR <mensaje>`.

