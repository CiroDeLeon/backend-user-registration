# Aplicación de Registro de Usuarios

## Descripción
Este proyecto es una aplicación Spring Boot que expone una API RESTful para el registro de usuarios. Implementa validaciones para el correo electrónico y la contraseña, autenticación con tokens JWT y utiliza una base de datos H2 en memoria para la persistencia de datos. El objetivo principal es permitir el registro de usuarios junto con sus números de teléfono en un formato específico.
Ademas se realizó el soporte de https usando un certificado autofirmado y todas las peticiones http se redirigen a https. 
tiene implementada la dockerizacion.
Permite instalacion en ambiente de desarrollo con h2
Permite instalacion en ambiente productivo con postgres

## Tecnologías Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok
- JWT (JSON Web Token)

## Requisitos
Para ejecutar este proyecto se necesita tener instalado:
- JDK 17 o superior
- Maven Versión 3.8.7 o Docker Version 20.10.21

## Instalación y Ejecución usando DOCKER (RECOMENDADO)

1. Clonar el repositorio:
```
git clone https://github.com/CiroDeLeon/backend-user-registration.git
```

2. Navegar al directorio del proyecto:
```
cd backend-user-registration
```
```
cd user-registration
```

3. Creamos la imagen
```
docker build -t  user-registration-img .
```

4. Creamos un contenedor
```
docker run -p 8080:8443 user-registration-img
```

5. si deseas crear otro contenedor que corra sobre otro puerto,en este caso en el 8081 el comando seria:
```
docker run -p 8081:8443 user-registration-img
```

## Instalación y Ejecución usando MAVEN
Sigue los siguientes pasos para instalar y ejecutar el proyecto:

1. Clonar el repositorio:
```
git clone https://github.com/CiroDeLeon/backend-user-registration.git
```

2. Navegar al directorio del proyecto:
```
cd backend-user-registration
```
```
cd user-registration
```

3. Generar el jar con Maven con el siguiente comando:
```
mvn clean install
```

4. ingresas a la carpeta target con el siguiente comando:
```
cd target
```

5. ejecutas el jar
```
java -jar app.jar
```




Una vez iniciada, la aplicación estará disponible en 
`https://localhost:8080`

puedes acceder a la documentacion swagger
```
https://localhost:8080/swagger-ui/index.html
```

puedes acceder a la h2-console 
```
https://localhost:8080/h2-console
```
## credenciales h2-console
JDBC URL
```
jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
```

User Name
```
sa
```

password
```
123456
```

## Uso de la API
Para registrar un nuevo usuario, enviar una petición POST a `https://localhost:8080/users/register` con el siguiente cuerpo JSON:

```json
{
  "name": "Juan Rodriguez",
  "email": "juan@dominio.cl",
  "password": "10784054P@ta",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "countrycode": "57"
    }
  ]
}
```
Respuestas
Éxito: Código de estado HTTP 200 OK y un cuerpo JSON con los datos del usuario, incluyendo id, created, modified, lastLogin, token, y isActive.
Error: Código de estado HTTP adecuado y un cuerpo JSON con el mensaje de error.

Para hacer login se debe enviar una petición POST a `https://localhost:8080/users/login` con el siguiente JSON:

```json
{
  "email": "juan@dominio.cl",
  "password": "10784054P@ta"
}
```

Para listar todos los usuarios se debe enviar una petición GET a `https://localhost:8080/users/all` anexando un header con la siguiente estructura porque este endpoint esta asegurado:

Authorization=Bearer TokenValue


Configuración
La configuración de la aplicación se puede ajustar en el archivo src/main/resources/application.properties. Esto incluye la conexión a la base de datos, la configuración de JPA y la expresión regular para la validación de la contraseña.

## Diagrama UML 

![Ejemplo de Imagen](DiagramaDeApp.png "Este es un ejemplo de imagen")

## Instalacion Ambiente Productivo

primero instalar en ambiente de desarrollo.

Usando Docker (RECOMENDADO):

Las instrucciones estan en el leeme.txt de la carpeta docker-files

Usando maven

1) Ajustar el  applications.properties ajustar la propiedad:

```
spring.profiles.active=prod
```

2) Instalar Postgres

3) Crear la base de datos con nombre : proddb

4) Correr el script de creación de tablas de postgres de la carpeta sql-scripts/create-script-postgres.sql

En WINDOWS

```
psql -h localhost -d proddb -U postgres -f .\sql-scripts\create-script-postgres.sql
```

En LINUX

```
psql -h localhost -d proddb -U postgres -f ./sql-scripts/create-script-postgres.sql
```

5) ingresar a la raiz del proyecto y correr comando 

```
mvn clean install -Pprod
```

6) correr comandos

```
cd target
```

```
java -jar app.jar
```






