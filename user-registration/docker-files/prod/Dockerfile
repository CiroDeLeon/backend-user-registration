# Etapa de construcción para compilar la aplicación
FROM maven:3.6.3-openjdk-17-slim AS build

WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente del proyecto
COPY src src

# Empaquetar la aplicación sin ejecutar los tests
RUN mvn clean install -DskipTests -Pprod

# Etapa de ejecución para correr la aplicación
FROM openjdk:17-slim

# Información del mantenedor (opcional)
LABEL maintainer="elalumnopc@hotmail.com"

# Copiar el jar de la etapa de construcción a la etapa de ejecución
COPY --from=build /app/target/app.jar app.jar

# Instalar Postgres
RUN apt-get update && \
    apt-get install -y postgresql postgresql-contrib && \
    rm -rf /var/lib/apt/lists/* 

# Copiar el script de inicio personalizado
COPY start.sh /usr/local/bin/start.sh
RUN chmod +x /usr/local/bin/start.sh

# Copiar los scripts SQL
COPY sql-scripts/create-script-postgres.sql /docker-entrypoint-initdb.d/

# Ejecutar el script de inicio personalizado que arrancará PostgreSQL y luego la aplicación
ENTRYPOINT ["/usr/local/bin/start.sh"]





