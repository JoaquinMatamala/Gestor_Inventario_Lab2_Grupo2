
# Lab1TBD

Laboratorio 1 Taller Base de Datos.

## DataBase
- Crear la base de datos en PostgreSQL (pgAdmin 4) con el nombre de "Lab1TBD":
- En "\Backend\src\main\resources\datos" encontrarás el script "createDB.sql" para crear la base de datos.
- Ejecutar la primera linea como una query en pgAdmin 4 (o directamente usar la intefaz de usuario para crearla con el nombre especificado).
- Ejecutar el resto del script "createDB.sql" como query dentro de la base de datos "Lab1TBD" creada.
- En "\Backend\src\main\resources\datos" encontrarás el script "functions.sql" para la crear los triggers.
- Copiar el query en pgAdmin 4 para que se generen los triggers.

## BACKEND
- En aplication.properties se define el puerto que se usará para el backend, por defecto será el 8070.

- En la ruta principal de la aplicación, en el package "config" se encuentra el archivo "DataBaseContext.java", 
  el cual contiene la configuración de la base de datos, en este caso PostgreSQL. De ser necesario, deberás ingresar tus
  propias credenciales y/o el puerto que estés utilizando para la base de datos.

- Si todo está correctamente configurado, al ejecutar "Lab1TBDApplication.java" se iniciará el backend en el puerto especificado.

## FRONTEND

### Requisitos:

NodeJS (npm):

### Ir a la carpeta del frontend.

```bash
  cd ./Frontend
```
### Instalación de paquetes.

```bash
  npm install
```
### Compilación y recarga en host para desarrollo.

```bash
  npm run serve
```

### Compilación y minimización de producción.

```bash
  npm run build
```

### Lints y archivos de arreglos.

```bash
  npm run lint
```

## Versiones

- Project: Maven
- Language: Java
- Spring Boot: 3.3.2
- Packaging: Jar
- Java: 17


