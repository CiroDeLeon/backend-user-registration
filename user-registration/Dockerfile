# Usar la imagen base de OpenJDK con la versión 17
FROM openjdk:17-slim as build

# Información del mantenedor (opcional)
LABEL maintainer="elalumnopc@hotmail.com"

# Instalar Maven
RUN apt-get update && \
    apt-get install -y maven

# Crear el directorio de la aplicación
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente del proyecto
COPY src src

# Empaquetar la aplicación sin ejecutar los tests
RUN mvn clean install -DskipTests

# Establecer la imagen base con JRE para ejecución
FROM openjdk:17-slim

# Copiar el jar de la etapa de construcción a la etapa de ejecución
COPY --from=build /app/target/app.jar app.jar

# Ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]


