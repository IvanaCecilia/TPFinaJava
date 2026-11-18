# Creustacico

Creustacico es una aplicacion desarrollada en Java como trabajo practico final de la materia Tecnologias Java.

El proyecto implementa un servidor de consola basado en sockets TCP y un framework propio que permite registrar recursos, acciones, servicios y sesiones de usuario. Actualmente incluye una aplicacion de ejemplo para iniciar sesion, consultar el usuario activo y cerrar sesion.

## Integrantes

- Dario Salvetti - DNI 41427479
- Matias Beratz - DNI 44933215
- Ivana Schwindt - DNI 26704897

## Objetivo del proyecto

El objetivo es practicar y demostrar los conceptos principales de Java mediante una arquitectura modular:

- Programacion orientada a objetos.
- Comunicacion cliente-servidor mediante sockets.
- Concurrencia con hilos.
- Manejo de solicitudes y respuestas por texto.
- Registro y despacho de acciones.
- Gestion de sesiones.
- Separacion entre framework y aplicacion.
- Manejo basico de roles y permisos.

## Requisitos

- Java JDK 17 o superior.
- Visual Studio Code con la Extension Pack for Java, opcional para ejecutar desde el editor.

Para verificar la instalacion de Java:

```bat
java -version
javac -version
```

## Ejecucion

Abrir una terminal en la carpeta `tpfinal` y compilar todos los archivos Java:

```bat
javac -d bin src\app\framework\*.java src\app\main\*.java src\cliente\*.java
```

Luego iniciar el servidor:

```bat
java -cp bin app.main.Main
```

El servidor quedara disponible en el puerto `3000`. En otra terminal, iniciar el cliente de consola:

```bat
java -cp bin cliente.ClienteConsola
```

Tambien se puede indicar el host y el puerto:

```bat
java -cp bin cliente.ClienteConsola localhost 3000
```

Para desconectarse desde el cliente, escribir `salir`.

## Uso rapido

Al conectarse, el servidor responde con `Bienvenido`.

Las solicitudes utilizan el siguiente formato:

```text
recurso/accion/parametro1/parametro2
```

Las respuestas exitosas comienzan con `OK` y los errores controlados comienzan con `ERROR`.

### Iniciar sesion

Usuarios disponibles:

| Usuario | Clave | Rol |
|---|---|---|
| ana | 1234 | USUARIO |
| beto | 4321 | USUARIO |
| admin | admin | ADMIN |

Ejemplo:

```text
usuarios/login/ana/1234
```

Respuesta esperada:

```text
OK Hola, ana (USUARIO)
```

### Consultar el usuario logueado

```text
usuarios/usuarioLogueado
```

Ejemplo de respuesta con sesion activa:

```text
OK Usuario logueado: ana (USUARIO)
```

Ejemplo sin sesion activa:

```text
OK (nadie)
```

### Cerrar sesion

```text
usuarios/logout
```

## Arquitectura

### Framework

- `Servidor`: abre el puerto TCP, acepta conexiones y crea una sesion para cada cliente.
- `Comunicador`: envia y recibe mensajes de texto por la conexion.
- `Router`: relaciona recursos con controladores y acciones.
- `Solicitud`: representa el recurso, la accion y sus parametros.
- `ParsearSolicitud`: convierte una linea de texto en una solicitud.
- `Controlador`: interfaz que deben implementar los controladores.
- `Servicios`: registro simple de servicios disponibles para los controladores.
- `Sesion`: mantiene el usuario y el rol asociados a una conexion.
- `Rol`: define los roles `USUARIO` y `ADMIN`.
- `ExcepcionesFramework`: contiene las excepciones utilizadas por el framework.

### Aplicacion

- `Main`: inicia el servidor en el puerto `3000`, registra los servicios y controladores.
- `ControladorUsuarios`: expone las acciones `login`, `logout` y `usuarioLogueado`.
- `ServicioUsuarios`: contiene los usuarios y realiza la autenticacion.
- `Usuario`: representa los datos basicos de un usuario.
- `ClienteConsola`: permite enviar solicitudes al servidor desde la consola.

## Estructura del proyecto

```text
tpfinal/
|-- src/
|   |-- app/
|   |   |-- framework/       Implementacion del framework
|   |   `-- main/            Aplicacion y modelo de usuarios
|   `-- cliente/             Cliente TCP de consola
|-- bin/                     Archivos compilados
|-- lib/                     Librerias externas
`-- README.md
```

## Comportamiento de las respuestas

El servidor procesa una solicitud por linea. Por cada solicitud valida el formato y busca el recurso y la accion registrados.

- Solicitud valida: `OK <mensaje>`
- Solicitud o parametro invalido: `ERROR <mensaje>`
- Accion o recurso inexistente: respuesta de error controlada por el servidor
- Error inesperado: `Error interno del servidor`

Cada conexion mantiene su propia sesion. Esto significa que iniciar sesion en un cliente no afecta la sesion de otro cliente conectado.