#!/bin/bash

# Iniciar el servicio PostgreSQL
/etc/init.d/postgresql start

# Esperar a que PostgreSQL esté listo para aceptar comandos
while ! pg_isready; do
    sleep 1
done

# Crear el rol de usuario y la base de datos si no existen
su - postgres -c "psql -tAc \"SELECT 1 FROM pg_roles WHERE rolname='postgres'\" | grep -q 1 || createuser -s postgres"
su - postgres -c "psql -tAc \"SELECT 1 FROM pg_database WHERE datname='proddb'\" | grep -q 1 || createdb -O postgres proddb"

# Establecer la contraseña del usuario postgres
su - postgres -c "psql -c \"ALTER USER postgres WITH PASSWORD 'mysecretpassword';\""

# Ejecutar el script SQL para configurar el esquema de la base de datos, si es necesario
su - postgres -c "psql -d proddb -a -f /docker-entrypoint-initdb.d/create-script-postgres.sql"

# Iniciar la aplicación Java
exec java -jar /app.jar





